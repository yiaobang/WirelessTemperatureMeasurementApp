package com.y.wirelesstemperaturemeasurement

import android.app.Application
import android.content.Context
import com.y.wirelesstemperaturemeasurement.config.Config
import com.y.wirelesstemperaturemeasurement.data.listener.connection
import com.y.wirelesstemperaturemeasurement.data.listener.disConnection
import com.y.wirelesstemperaturemeasurement.room.DataBase
import com.y.wirelesstemperaturemeasurement.utils.deleteTime

lateinit var dataBase: DataBase

fun initApp(context: Context) {
    Config.initialize(context)
    //数据库初始化
    dataBase = DataBase.getDataBase(context)
    //获取30天前的时间
    val deleteTime = deleteTime()
    //删除过期数据
    dataBase.sensorDataDao().deleteOldData(deleteTime)
    dataBase.sensorEventDao().deleteOldEvent(deleteTime)
    //连接串口
//    connection("ttyS1")
}

fun closeApp() {
    dataBase.close()
    disConnection()
}