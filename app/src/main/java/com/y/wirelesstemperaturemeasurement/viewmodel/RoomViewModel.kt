package com.y.wirelesstemperaturemeasurement.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.data.listener.connection
import com.y.wirelesstemperaturemeasurement.data.listener.disConnection
import com.y.wirelesstemperaturemeasurement.data.parse.temperature
import com.y.wirelesstemperaturemeasurement.data.parse.voltageRH
import com.y.wirelesstemperaturemeasurement.room.Data
import com.y.wirelesstemperaturemeasurement.room.DataBase
import com.y.wirelesstemperaturemeasurement.room.DataDao
import com.y.wirelesstemperaturemeasurement.room.EventDao
import com.y.wirelesstemperaturemeasurement.room.JointDao
import com.y.wirelesstemperaturemeasurement.room.Parts
import com.y.wirelesstemperaturemeasurement.room.PartsDao
import com.y.wirelesstemperaturemeasurement.room.haveId
import com.y.wirelesstemperaturemeasurement.room.haveSerialNumber
import com.y.wirelesstemperaturemeasurement.timeSumInit
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.utils.now
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.SensorDataMap
import kotlinx.coroutines.launch

object RoomViewModel : ViewModel() {

    private lateinit var DATA_BASE: DataBase

    private lateinit var PARTS_DAO: PartsDao
    private lateinit var DATE_DAO: DataDao
    private lateinit var EVENT_DAO: EventDao
    private lateinit var JOINT_DAO: JointDao


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
        JOINT_DAO = DATA_BASE.jointDao()
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
            EVENT_DAO.deleteOldEvent()
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
        oldId: Int,
        newId: Int,
        deviceName: String,
        partsName: String,
        serialNumber: Long,
        newType: Byte,
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

    fun deleteParts(id: Int, context: Context, updateInfo: (Parts) -> Unit) {
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
    fun queryHistory(){

    }

    /**
     * 添加测温点数据
     * @param [serialNumber] 序列号
     * @param [msg] 消息
     */
    fun addPartsData(serialNumber: Long, msg: ByteArray) {
        viewModelScope.launch {
            //查询测温点ID
            val selectById = PARTS_DAO.selectById(serialNumber)
            if (selectById != null) {
                val newTemp = temperature(msg[9], msg[10])
                val newVoltageRH = voltageRH(msg[11], msg[12])
                val newData = Data(
                    partsId = selectById,
                    temperature = newTemp,
                    voltageRH = newVoltageRH,
                    rssi = msg[15]
                )
                DATE_DAO.insert(newData)
                Log.i(TAG, "添加历史数据: $newData")
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
     * 更新数据列表
     *
     */
    fun updateData() {
        viewModelScope.launch {
            SensorDataMap = JOINT_DAO.dataShow().associateBy { it.serialNumber }
            SensorDataMap.forEach { (index, data) -> Log.d(TAG, "$index = $data") }
            StateViewModel.dataTime = now()
        }
    }
    fun closeDataBase() {
        disConnection()
        handler.removeCallbacks(updateData)
        DATA_BASE.close()
    }
}