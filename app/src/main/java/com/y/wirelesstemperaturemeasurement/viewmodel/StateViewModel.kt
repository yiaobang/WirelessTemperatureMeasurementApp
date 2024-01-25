package com.y.wirelesstemperaturemeasurement.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.room.DataShow
import com.y.wirelesstemperaturemeasurement.room.JOINT_DAO
import com.y.wirelesstemperaturemeasurement.room.PARTS_DAO
import com.y.wirelesstemperaturemeasurement.room.Parts

object StateViewModel : ViewModel() {
    //串口状态
    var SerialPort by mutableStateOf(false)

    //软件版本
    var SOFT by mutableStateOf("")
    //测温点数量
    var partsNumber by mutableIntStateOf(0)

    //硬件版本
    var HARD by mutableStateOf("")

    //设备列表
    var PARTS by mutableStateOf<List<MutableList<Parts>>>(listOf())
        private set

    //数据
    var SensorDataMap by mutableStateOf<Map<Int, DataShow>>(mapOf())
        private set

    /**
     * 更新测温点映射
     *
     */

    fun updateParts() {
        val selectAllParts = PARTS_DAO.selectAllParts()
        partsNumber = selectAllParts.size
        PARTS = selectAllParts.groupByTo(LinkedHashMap()) { it.deviceName }.values.toList()
    }

    fun updateData() {
        SensorDataMap = JOINT_DAO.dataShow().associateBy { it.serialNumber }
        SensorDataMap.forEach { (index, data) -> Log.d(TAG, "$index = $data") }
    }


    var audible by mutableStateOf(true)
        private set
    var warn by mutableIntStateOf(0)
    var error by mutableIntStateOf(0)
    var event by mutableIntStateOf(0)

    fun updateAudible() {
        audible = !audible
    }

    fun updateAudible(b: Boolean) {
        audible = b
    }
}