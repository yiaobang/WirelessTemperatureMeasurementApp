package com.y.wirelesstemperaturemeasurement.utils


import java.text.SimpleDateFormat

fun Byte.sensorType() = when (this) {
    1.toByte() -> "温湿度"
    else -> "温度"
}
fun String.sensorType(): Byte = when (this) {
    "温湿度" -> 1
    else -> 0
}

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
fun Long.dateTime(): String = simpleDateFormat.format(this)
