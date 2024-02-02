package com.y.wtm.room

import com.y.wtm.config.THSensorAlarmMinRH
import com.y.wtm.config.THSensorAlarmMinTemp
import com.y.wtm.config.THSensorMinRH
import com.y.wtm.config.THSensorMinTemp
import com.y.wtm.config.TSensorAlarmMinTemp
import com.y.wtm.config.TSensorMinTemp
import com.y.wtm.serialport.RH
import com.y.wtm.serialport.TEMP
import com.y.wtm.serialport.VOLTAGE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NowData?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun NowData?.voltageRH(): String = if (this == null) "--" else voltageRH(this.type, this.voltageRH)
fun HistoryData?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun HistoryData?.voltageRH(): String =
    if (this == null) "--" else voltageRH(this.type, this.voltageRH)

fun ShowEvent?.temp(): String = if (this == null) "--" else temp(this.temperature)
fun ShowEvent?.voltageRH(): String =
    if (this == null) "--" else voltageRH(this.type, this.voltageRH)

fun temp(temperature: Int): String = "${temperature / 100.0}$TEMP"
fun voltageRH(type: Int, voltageRH: Int): String = when (type) {
    1 -> "${voltageRH / 1000.0}$VOLTAGE"
    2 -> "${voltageRH / 100.0}$RH"
    else -> "--"
}


fun Int.sensorType() = when (this) {
    1 -> "温度"
    2 -> "温湿度"
    else -> ""
}

fun String.sensorType(): Int = when (this) {
    "温度" -> 1
    "温湿度" -> 2
    else -> 0
}

/**
 * 事件级别
 *
 */
fun Int.event() = when (this) {
    10 -> "温度预警"
    20 -> "温度报警"
    1 -> "湿度预警"
    2 -> "湿度报警"
    11 -> "温湿度预警"
    12 -> "温度预警湿度报警"
    21 -> "温度报警湿度预警"
    22 -> "温湿度报警"
    else -> ""
}

/**
 * 事件级别
 * @return [Byte]
 */
fun String.event(): Int = when (this) {
    "温度预警" -> 10
    "温度报警" -> 20
    "湿度预警" -> 1
    "湿度报警" -> 2
    "温湿度预警" -> 11
    "温度预警湿度报警" -> 12
    "温度报警湿度预警" -> 21
    "温湿度报警" -> 22
    else -> 0
}

/**
 * 事件描述
 * @param [temp]
 * @return [String]
 */
fun Int.eventMsg(temp: Int): String = when (this) {
    10 -> if (temp < TSensorMinTemp) "温低" else "温高"
    20 -> if (temp < TSensorAlarmMinTemp) "温低" else "温高"
    else -> ""
}

/**
 * 事件描述
 * @param [temp]
 * @param [rh]
 * @return [String]
 */
fun Int.eventMsg(temp: Int, rh: Int): String = when (this) {
    10 -> if (temp < THSensorMinTemp) "温高" else "温低"
    20 -> if (temp < THSensorAlarmMinTemp) "温高" else "温低"
    1 -> if (rh < THSensorMinRH) "湿高" else "湿低"
    2 -> if (rh < THSensorAlarmMinRH) "湿高" else "湿低"
    11 -> {
        if (temp < THSensorMinTemp) {
            if (rh < THSensorMinRH) {
                "温低湿低"
            } else {
                "温低湿高"
            }
        } else {
            if (rh < THSensorMinRH) {
                "温高湿低"
            } else {
                "温高湿高"
            }
        }
    }

    12 -> {
        if (temp < THSensorMinTemp) {
            if (rh < THSensorAlarmMinRH) {
                "温低湿低"
            } else {
                "温低湿高"
            }
        } else {
            if (rh < THSensorAlarmMinRH) {
                "温高湿低"
            } else {
                "温高湿高"
            }
        }
    }

    21 -> {
        if (temp < THSensorAlarmMinTemp) {
            if (rh < THSensorMinRH) {
                "温低湿低"
            } else {
                "温低湿高"
            }
        } else {
            if (rh < THSensorAlarmMinTemp) {
                "温高湿低"
            } else {
                "温高湿高"
            }
        }
    }

    22 -> {
        if (temp < THSensorAlarmMinTemp) {
            if (rh < THSensorAlarmMinRH) {
                "温低湿低"
            } else {
                "温低湿高"
            }
        } else {
            if (rh < THSensorAlarmMinTemp) {
                "温高湿低"
            } else {
                "温高湿高"
            }
        }
    }

    else -> ""
}

val local: Locale = Locale.getDefault()
private val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", local)
private val date = SimpleDateFormat("yyyy-MM-dd", local)
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
        this.toShort()
        true
    } catch (e: Exception) {
        false
    }
}

fun String.isPort(): Boolean {
    if (this == "") {
        return true
    }
    return try {
        this.toUShort()
        true
    } catch (e: Exception) {
        false
    }
}

fun String.isIPv4Address(): Boolean {
    val ipv4Pattern = """^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$""".toRegex()
    return ipv4Pattern.matches(this) && this.split(".").all { it.toIntOrNull() in 0..255 }
}

/**
 * 将一个Long类型拆成4个Short类型
 *
 * @return
 */
fun Long.toShorts(): Array<Short> {
    val shorts = Array(4) { 0.toShort() }
    for (i in 0 until 4) {
        val shift = i * 16
        shorts[i] = ((this shr shift) and 0xFFFF).toShort()
    }
    return shorts
}

/**
 * 将4个Short类型转成Long
 *
 * @return
 */
fun Array<Short>.toLong(): Long {
    var longValue: Long = 0

    for (i in 0 until 4) {
        val shift = i * 16
        longValue = longValue or ((this[i].toLong() and 0xFFFF) shl shift)
    }
    return longValue
}