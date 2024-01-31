package com.y.wirelesstemperaturemeasurement.room

import com.y.wirelesstemperaturemeasurement.data.parse.RH
import com.y.wirelesstemperaturemeasurement.data.parse.TEMP
import com.y.wirelesstemperaturemeasurement.data.parse.VOLTAGE
import java.text.SimpleDateFormat
import java.util.Date

fun NowData?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun NowData?.voltageRH(): String = if (this == null) "--" else voltageRH(this.type, this.voltageRH)
fun HistoryData?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun HistoryData?.voltageRH(): String =
    if (this == null) "--" else voltageRH(this.type, this.voltageRH)

fun ShowEvent?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun ShowEvent?.voltageRH(): String =
    if (this == null) "--" else voltageRH(this.type, this.voltageRH)

private fun temp(temperature: Double): String = "${temperature}$TEMP"
private fun voltageRH(type: Byte, voltageRH: Short): String = when (type) {
    1.toByte() -> "${voltageRH / 1000.0}$VOLTAGE"
    2.toByte() -> "${voltageRH / 100.0}$RH"
    else -> "--"
}

fun Byte.sensorType() = when (this) {
    1.toByte() -> "温度"
    2.toByte() -> "温湿度"
    else -> ""
}

fun String.sensorType(): Byte = when (this) {
    "温度" -> 1
    "温湿度" -> 2
    else -> 0
}

fun Byte.event() = when (this) {
    1.toByte() -> "告警"
    2.toByte() -> "报警"
    else -> ""
}

fun String.event(): Byte = when (this) {
    "告警" -> 1
    "报警" -> 2
    else -> 0
}


private val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
private val date = SimpleDateFormat("yyyy-MM-dd")

fun currentDate(): String = date.format(Date())
fun Long.toDate(): String = date.format(Date(this))
fun currentDateTime(): String = dateTime.format(Date())
fun Long.toDateTime(): String = dateTime.format(Date(this))


fun List<Parts>.haveId(id: Long): Boolean {
    this.forEach { if (it.id == id) return true }
    return false
}

fun List<Parts>.haveSerialNumber(serialNumber: Long): Boolean {
    this.forEach { if (it.serialNumber == serialNumber) return true }
    return false
}

fun String.isNumber(): Boolean {
    return try {
        if (this == "") {
            return true
        }
        if (this.toUInt() == 0.toUInt()) {
            false
        } else {
            true
        }
    } catch (e: Exception) {
        false
    }
}

fun String.isID(): Boolean {
    return try {
        if (this == "") {
            return true
        }
        if (this.toUShort() == 0.toUShort()) {
            false
        } else {
            true
        }
    } catch (e: Exception) {
        false
    }
}

fun String.isNumberParameter(): Boolean {
    if (this == "" || this == "-") {
        return true
    }
    return try {
        val toShort = this.toShort()
        true
    } catch (e: Exception) {
        false
    }
}

fun isIPv4Address(input: String): Boolean {
    val ipv4Pattern = """^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$""".toRegex()
    return ipv4Pattern.matches(input) && input.split(".").all { it.toIntOrNull() in 0..255 }
}