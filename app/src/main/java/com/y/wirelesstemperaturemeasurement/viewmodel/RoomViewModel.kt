package com.y.wirelesstemperaturemeasurement.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.wirelesstemperaturemeasurement.DAY
import com.y.wirelesstemperaturemeasurement.MONTH
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.data.listener.connection
import com.y.wirelesstemperaturemeasurement.data.listener.disConnection
import com.y.wirelesstemperaturemeasurement.data.parse.eventLevelCheck
import com.y.wirelesstemperaturemeasurement.data.parse.temperature
import com.y.wirelesstemperaturemeasurement.data.parse.voltageRH
import com.y.wirelesstemperaturemeasurement.room.Data
import com.y.wirelesstemperaturemeasurement.room.DataBase
import com.y.wirelesstemperaturemeasurement.room.Event
import com.y.wirelesstemperaturemeasurement.room.Parts
import com.y.wirelesstemperaturemeasurement.room.currentDateTime
import com.y.wirelesstemperaturemeasurement.room.dao.DataDao
import com.y.wirelesstemperaturemeasurement.room.dao.EventDao
import com.y.wirelesstemperaturemeasurement.room.dao.HistoryDataDao
import com.y.wirelesstemperaturemeasurement.room.dao.NowDataDao
import com.y.wirelesstemperaturemeasurement.room.dao.PartsDao
import com.y.wirelesstemperaturemeasurement.room.dao.ShowEventDao
import com.y.wirelesstemperaturemeasurement.room.haveId
import com.y.wirelesstemperaturemeasurement.room.haveSerialNumber
import com.y.wirelesstemperaturemeasurement.timeSumInit
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.upload.MyMQTT
import com.y.wirelesstemperaturemeasurement.upload.MyModbus
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.SensorDataMap
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.event
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.historyData
import kotlinx.coroutines.launch

object RoomViewModel : ViewModel() {
    private lateinit var DATA_BASE: DataBase

    private lateinit var PARTS_DAO: PartsDao
    private lateinit var DATE_DAO: DataDao
    private lateinit var EVENT_DAO: EventDao
    private lateinit var HISTORY_DATA_DAO: HistoryDataDao
    private lateinit var NOW_DATA_DAO: NowDataDao
    private lateinit var SHOW_EVENT_DAO: ShowEventDao


    private val handler = Handler(Looper.getMainLooper())

    //更新任务
    private val updateData = object : Runnable {
        override fun run() {
            Log.d(TAG, "run: 更新最新数据")
            handler.postDelayed(this, 120000)
            updateData()
        }
    }

    /**
     * 初始化
     * @param [database]
     */
    fun initRoomViewModel(database: DataBase) {
        timeSumInit()
        this.DATA_BASE = database
        PARTS_DAO = DATA_BASE.partsDao()
        DATE_DAO = DATA_BASE.dataDao()
        EVENT_DAO = DATA_BASE.eventDao()
        HISTORY_DATA_DAO = DATA_BASE.historyDao()
        NOW_DATA_DAO = DATA_BASE.nowDataDao()
        SHOW_EVENT_DAO = DATA_BASE.showEventDao()
        deleteOldData()
        updateParts()
        handler.post(updateData)
        connection()
    }


    /**
     * 删除过期数据
     *
     */
    private fun deleteOldData() {
        viewModelScope.launch {
            DATE_DAO.deleteOldData()
        }
    }

    /**
     * 添加测温点
     * @param [parts]
     */
    fun addParts(parts: Parts, context: Context) {
        viewModelScope.launch {
            val partsList = PARTS_DAO.selectParts(
                parts.id,
                parts.deviceName,
                parts.partsName,
                parts.serialNumber
            )
            if (partsList!!.isEmpty()) {
                try {
                    PARTS_DAO.insert(parts)
                    showToast(context, "添加成功")
                    updateParts()
                } catch (e: Exception) {
                    showToast(context, "添加失败: ...")
                }
            } else {
                if (partsList.haveId(parts.id)) {
                    showToast(context, "添加失败:ID不允许重复")
                } else if (partsList.haveSerialNumber(parts.serialNumber)) {
                    showToast(context, "添加失败:序列号不允许重复")
                } else {
                    showToast(
                        context,
                        "添加失败:设备名称和测温点名称组合不允许重复"
                    )
                }
            }
        }
    }

    /**
     * 修改测温点
     * @param [oldId] 要修改的测温点的ID
     * @param [newId] 修改后的ID
     * @param [deviceName] 修改后的设备名称
     * @param [partsName] 修改后的测温点名称
     * @param [serialNumber] 修改后的传感器序列号
     * @param [type] 修改后的传感器类型
     */
    fun editParts(
        oldId: Long,
        newId: Long,
        deviceName: String,
        partsName: String,
        serialNumber: Long,
        newType: Int,
        context: Context
    ) {
        viewModelScope.launch {
            //查询要修改的测温点是否存在
            val oldParts = PARTS_DAO.selectId(oldId)
            if (oldParts == null) {
                showToast(context, "修改失败:要修改的测温点(id=$oldId)不存在")
            } else {
                val newParts = oldParts.copy()
                //修改后的测温点
                val partsList =
                    PARTS_DAO.selectParts(newId, deviceName, partsName, serialNumber)
                if (newId > 0) {
                    newParts.id = newId
                }
                if (deviceName != "") {
                    newParts.deviceName = deviceName
                }
                if (partsName != "") {
                    newParts.partsName = partsName
                }
                if (serialNumber > 0) {
                    newParts.serialNumber = serialNumber
                }
                newParts.type = newType
                if (partsList!!.isEmpty()) {
                    if (oldParts == newParts) {
                        showToast(context, "无需修改:修改前后信息一致")
                    } else {
                        PARTS_DAO.update(
                            oldId,
                            newParts.id,
                            newParts.deviceName,
                            newParts.partsName,
                            newParts.serialNumber,
                            newParts.type
                        )
                        showToast(context, "修改成功")
                        updateParts()
                    }
                } else {
                    try {
                        PARTS_DAO.update(
                            oldId,
                            newParts.id,
                            newParts.deviceName,
                            newParts.partsName,
                            newParts.serialNumber,
                            newParts.type
                        )
                        showToast(context, "修改成功")
                        updateParts()
                    } catch (e: Exception) {
                        if (oldId != newId && partsList.haveId(newId)) {
                            showToast(context, "修改失败:修改后的ID($newId)已存在")
                        } else if (partsList.haveSerialNumber(serialNumber)) {
                            showToast(context, "修改失败:修改后的序列号($serialNumber)已存在")
                        } else {
                            showToast(
                                context,
                                "修改失败:修改后的设备名称和测温点名称组合已存在"
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteParts(id: Long, context: Context, updateInfo: (Parts) -> Unit) {
        viewModelScope.launch {
            val parts = PARTS_DAO.selectId(id)
            if (parts == null) {
                showToast(context, "删除失败:要删除的测温点不存在")
            } else {
                updateInfo(parts)
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            PARTS_DAO.deleteId(id)
            updateParts()
        }
    }

    /**
     * 添加测温点数据
     * @param [serialNumber] 序列号
     * @param [msg] 消息
     */
    fun addPartsData(serialNumber: Long, msg: ByteArray) {
        viewModelScope.launch {
            //查询测温点ID
            val selectById = PARTS_DAO.selectSerialNumber(serialNumber)
            if (selectById != null) {
                val newTemp = temperature(msg[9], msg[10])
                val newVoltageRH = voltageRH(msg[11], msg[12])
                val newData = Data(
                    partsId = selectById,
                    temperature = newTemp,
                    voltageRH = newVoltageRH,
                    rssi = msg[15].toInt()
                )
                val dataId = DATE_DAO.insert(newData)
                addEvent(selectById,serialNumber,dataId,newData)
                Log.i(TAG, "添加历史数据: $newData")
            }
        }
    }

    /**
     * @param [partsId] 测温点ID
     * @param [serialNumber]序列号
     * @param [dateId]数据ID
     * @param [newData]数据
     */
    private fun addEvent(partsId: Int, serialNumber: Long, dateId: Long, newData: Data) {
        viewModelScope.launch {
            //传感器类型
            val nowData = SensorDataMap[serialNumber]
            if (nowData != null) {
                val eventLevelCheck = eventLevelCheck(nowData.type, newData)
                if (eventLevelCheck != null) {
                    //添加事件记录
                    EVENT_DAO.insert(
                        Event(
                            0,
                            dateId,
                            eventLevelCheck.eventLevel,
                            eventLevelCheck.eventMsg
                        )
                    )
                    MyMQTT.sendEvent(partsId,nowData.type,newData,eventLevelCheck)
                    MyModbus.updateMultipleRegisters(partsId,nowData.type,newData,eventLevelCheck.eventLevel)
                } else {
                    //不添加事件记录 正常通过MQTT  Modbus发送
                    MyModbus.updateMultipleRegisters(partsId,nowData.type,newData,0)
                    MyMQTT.sendData(partsId,nowData.type,newData)
                }
            }
        }
    }

    /**
     * 更新测温点列表
     *
     */
    fun updateParts() {
        viewModelScope.launch {
            val parts = PARTS_DAO.selectAllParts()
            StateViewModel.partsNumber = parts.size
            StateViewModel.PARTS =
                parts.groupByTo(LinkedHashMap()) { it.deviceName }.values.toList()
            updateData()
        }
    }

    /**
     * 查询历史数据
     * @param [id] 测温点id
     * @param [deviceName] 设备名称
     * @param [selectedDateMillis] 开始时间
     * @param [selectedDateMillis1] 结束时间
     */
    fun selectHistoryData(
        id: Long?,
        deviceName: String,
        start: Long?,
        end: Long?
    ) {
        val startTime = start ?: (System.currentTimeMillis() - 2 * MONTH)
        val endTime = (end ?: System.currentTimeMillis()) + DAY
        viewModelScope.launch {
            historyData = if (id == null) {
                if (deviceName == "") {
                    HISTORY_DATA_DAO.historyData(startTime, endTime)
                } else {
                    HISTORY_DATA_DAO.historyData(deviceName, startTime, endTime)
                }
            } else {
                HISTORY_DATA_DAO.historyData(id, startTime, endTime)
            }
        }
    }

    fun selectEvent(
        id: Long?,
        deviceName: String,
        eventLevel: Int,
        start: Long?,
        end: Long?
    ) {
        val startTime = start ?: (System.currentTimeMillis() - 2 * MONTH)
        val endTime = (end ?: System.currentTimeMillis()) + DAY
        viewModelScope.launch {
            event = if (id == null) {
                if (deviceName == "") {
                    if (eventLevel == 0) {
                        SHOW_EVENT_DAO.showEvent(startTime, endTime)
                    } else {
                        SHOW_EVENT_DAO.showEvent(eventLevel, startTime, endTime)
                    }
                } else {
                    if (eventLevel == 0) {
                        SHOW_EVENT_DAO.showEvent(deviceName, startTime, endTime)
                    } else {
                        SHOW_EVENT_DAO.showEvent(deviceName, eventLevel, startTime, endTime)
                    }
                }
            } else {
                if (eventLevel == 0) {
                    SHOW_EVENT_DAO.showEvent(id, startTime, endTime)
                } else {
                    SHOW_EVENT_DAO.showEvent(id, eventLevel, startTime, endTime)
                }
            }
        }
    }


    /**
     * 更新数据列表
     *
     */
    fun updateData() {
        viewModelScope.launch {
            SensorDataMap = NOW_DATA_DAO.currentData().associateBy { it.serialNumber }
            SensorDataMap.forEach { (index, data) -> Log.d(TAG, "$index = $data") }
            StateViewModel.dataTime = currentDateTime()
        }
    }

    fun closeDataBase() {
        disConnection()
        handler.removeCallbacks(updateData)
        DATA_BASE.close()
    }


}