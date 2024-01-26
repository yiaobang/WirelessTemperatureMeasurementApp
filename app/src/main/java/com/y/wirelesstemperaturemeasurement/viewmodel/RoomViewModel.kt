package com.y.wirelesstemperaturemeasurement.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import com.y.wirelesstemperaturemeasurement.timeSumInit
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
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
            updateData()
            Log.d(TAG, "run: 更新最新数据")
            handler.postDelayed(this, 120000)
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
            val selectParts = PARTS_DAO.selectParts(
                parts.id,
                parts.deviceName,
                parts.partsName,
                parts.serialNumber
            )
            if (selectParts == null) {
                try {
                    PARTS_DAO.insert(parts)
                    showToast(context, "添加成功")
                    updateParts()
                } catch (e: Exception) {
                    showToast(context, "添加失败: ...", Toast.LENGTH_LONG)
                }
            } else {
                if (parts.id == selectParts.id) {
                    showToast(context, "添加失败:ID不允许重复", Toast.LENGTH_LONG)
                } else if (parts.serialNumber == selectParts.serialNumber) {
                    showToast(context, "添加失败:序列号不允许重复", Toast.LENGTH_LONG)
                } else {
                    showToast(
                        context,
                        "添加失败:设备名称和测温点名称组合不允许重复",
                        Toast.LENGTH_LONG
                    )
                }
            }
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
            val partsId = PARTS_DAO.selectById(serialNumber)
            if (partsId != null) {
                val newTemp = temperature(msg[9], msg[10])
                val newVoltageRH = voltageRH(msg[11], msg[12])
                val newData = Data(
                    partsId = partsId,
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
        }
    }

    /**
     * 更新数据列表
     *
     */
    private fun updateData() {
        viewModelScope.launch {
            StateViewModel.SensorDataMap = JOINT_DAO.dataShow().associateBy { it.serialNumber }
            StateViewModel.SensorDataMap.forEach { (index, data) -> Log.d(TAG, "$index = $data") }
        }
    }


    fun closeDataBase() {
        disConnection()
        handler.removeCallbacks(updateData)
        DATA_BASE.close()
    }
}