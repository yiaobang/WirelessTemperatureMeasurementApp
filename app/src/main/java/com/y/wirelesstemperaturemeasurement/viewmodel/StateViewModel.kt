package com.y.wirelesstemperaturemeasurement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.y.wirelesstemperaturemeasurement.config.Config.readConfig
import com.y.wirelesstemperaturemeasurement.config.Config.writeConfig
import com.y.wirelesstemperaturemeasurement.room.Data
import com.y.wirelesstemperaturemeasurement.room.DataShow
import com.y.wirelesstemperaturemeasurement.room.Parts

object StateViewModel : ViewModel() {
    //软件版本
    var SOFT by mutableStateOf("")

    //硬件版本
    var HARD by mutableStateOf("")

    //测温点数量
    var partsNumber by mutableIntStateOf(0)

    //历史数据查询
    var historyData by mutableStateOf<List<Data>>(listOf())

    //更新时间
    var dataTime by mutableStateOf("")

    //测温点列表
    var PARTS by mutableStateOf<List<MutableList<Parts>>>(listOf())

    //每个传感器最新的数据
    var SensorDataMap by mutableStateOf<Map<Long, DataShow>>(mapOf())

    //声音
    var audible by mutableStateOf(readConfig("audible", "true").toBoolean())

    //预警消息数量
    var warn by mutableIntStateOf(0)

    //告警消息数量
    var error by mutableIntStateOf(0)

    fun updateAudible() {
        audible = !audible
        writeConfig("audible", audible.toString())
    }
}