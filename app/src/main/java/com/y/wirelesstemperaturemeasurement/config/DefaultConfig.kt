package com.y.wirelesstemperaturemeasurement.config

import com.y.wirelesstemperaturemeasurement.data.listener.serialPortNames
import com.y.wirelesstemperaturemeasurement.ui.Parameter

val serialNumbersParameter = Parameter("串口号", serialPortNames)
val baudRateParameter = Parameter("波特率", listOf("19200", "115200"))
val dataBitsParameter = Parameter("数据位", listOf("5", "6", "7", "8"))
val stopBitsParameter = Parameter("停止位", listOf("1", "1.5", "2"))
val parityParameter = Parameter("校验", listOf("无", "奇校验", "偶校验", "标记校验", "空格校验"))
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

