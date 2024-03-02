package com.y.wtm.config

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.y.wtm.TAG
import com.y.wtm.data.driver.DriverType
import com.y.wtm.data.serialPorts
import com.y.wtm.data.usbSerialDrivers
import com.y.wtm.ui.Parameter

@SuppressWarnings("all")
object Config {
    //配置文件名称
    private const val CONFIG_NAME = "WirelessTemperatureMeasurementAppConfig"
    private lateinit var context: Context
    private lateinit var read: SharedPreferences
    private lateinit var write: SharedPreferences.Editor
    fun initialize(context: Context) {
        Log.d(TAG, "Config: 初始化配置文件")
        this.context = context
        read = Config.context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE)
        write = read.edit()
    }

    fun readConfig(key: String, def: String = "") = read.getString(key, def).toString()
    fun writeConfig(key: String, value: String) {
        Log.d(TAG, "修改配置文件: $key = $value")
        write.putString(key, value)
        write.apply()
    }
}

val driverTypeParameter =
    Parameter("通信方式", DriverType.entries.map { DriverType.driverType(it) })
val usbNumbersParameter = Parameter("USB", usbSerialDrivers.map { it.device.serialNumber as String })
val serialNumbersParameter = Parameter("COM", serialPorts.map { it.systemPortName })
val baudRateParameter = Parameter("波特率", listOf("19200", "115200"))
val dataBitsParameter = Parameter("数据位", listOf("5", "6", "7", "8"))
val stopBitsParameter = Parameter("停止位", listOf("1", "1.5", "2"))
val parityParameter = Parameter("校验方式", listOf("无", "奇校验", "偶校验", "标记校验", "空格校验"))
val flowParameter = Parameter("流控", listOf("无", "RTS/CTS", "DSR/DTR", "ON/OFF"))
val sensorType = listOf("温度", "温湿度")
val eventLevels = listOf(
    "所有级别",
    "温度预警",
    "温度报警",
    "湿度预警",
    "湿度报警",
    "温湿度预警",
    "温度预警湿度报警",
    "温度报警湿度预警",
    "温湿度报警"
)

//温度传感器
var TSensorMinTemp = Config.readConfig("sensor:T-minT", "2000").toInt()
var TSensorMaxTemp = Config.readConfig("sensor:T-maxT", "8000").toInt()
var TSensorAlarmMinTemp = Config.readConfig("sensor:T-alarmMinT", "0").toInt()
var TSensorAlarmMaxTemp = Config.readConfig("sensor:T-alarmMaxT", "10000").toInt()

//温湿度传感器
var THSensorMinTemp = Config.readConfig("sensor:TH-minT", "2000").toInt()
var THSensorMaxTemp = Config.readConfig("sensor:TH-maxT", "8000").toInt()
var THSensorAlarmMinTemp = Config.readConfig("sensor:TH-alarmMinT", "0").toInt()
var THSensorAlarmMaxTemp = Config.readConfig("sensor:TH-alarmMaxT", "10000").toInt()
var THSensorMinRH = Config.readConfig("sensor:TH-minT", "3000").toInt()
var THSensorMaxRH = Config.readConfig("sensor:TH-maxT", "7000").toInt()
var THSensorAlarmMinRH = Config.readConfig("sensor:TH-alarmMinT", "1000").toInt()
var THSensorAlarmMaxRH = Config.readConfig("sensor:TH-alarmMaxT", "10000").toInt()

fun Int.show() = "${this / 100}"
fun Int.slave() = "${this * 100}"