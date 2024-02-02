package com.y.wtm.serialport

import com.fazecast.jSerialComm.SerialPort.EVEN_PARITY
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_CTS_ENABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_DISABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_DSR_ENABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_DTR_ENABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_RTS_ENABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED
import com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED
import com.fazecast.jSerialComm.SerialPort.MARK_PARITY
import com.fazecast.jSerialComm.SerialPort.NO_PARITY
import com.fazecast.jSerialComm.SerialPort.ODD_PARITY
import com.fazecast.jSerialComm.SerialPort.ONE_POINT_FIVE_STOP_BITS
import com.fazecast.jSerialComm.SerialPort.ONE_STOP_BIT
import com.fazecast.jSerialComm.SerialPort.SPACE_PARITY
import com.fazecast.jSerialComm.SerialPort.TWO_STOP_BITS

fun Int.baudRate() = this.toString()
fun Int.dataBits() = this.toString()
fun Int.stopBits() = when (this) {
    ONE_POINT_FIVE_STOP_BITS -> "1.5"
    TWO_STOP_BITS -> "2"
    else -> "1"
}

fun Int.parity() = when (this) {
    ODD_PARITY -> "奇校验"
    EVEN_PARITY -> "偶校验"
    MARK_PARITY -> "标记位校验"
    SPACE_PARITY -> "空格校验"
    else -> "无"
}

fun Int.flow() = when (this) {
    FLOW_CONTROL_RTS_ENABLED or FLOW_CONTROL_CTS_ENABLED -> "RTS/CTS"
    FLOW_CONTROL_DSR_ENABLED or FLOW_CONTROL_DTR_ENABLED -> "DSR/DTR"
    FLOW_CONTROL_XONXOFF_IN_ENABLED or FLOW_CONTROL_XONXOFF_OUT_ENABLED -> "ON/OFF"
    else -> "无"
}


fun String.baudRate(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        19200
    }
}

fun String.dataBits(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        8
    }
}

fun String.stopBits() = when (this) {
    "1.5" -> ONE_POINT_FIVE_STOP_BITS
    "2" -> TWO_STOP_BITS
    else -> ONE_STOP_BIT
}

fun String.parity() = when (this) {
    "奇校验" -> ODD_PARITY
    "偶校验" -> EVEN_PARITY
    "标记位校验" -> MARK_PARITY
    "空格校验" -> SPACE_PARITY
    else -> NO_PARITY
}

fun String.flow() = when (this) {
    "RTS/CTS" -> FLOW_CONTROL_RTS_ENABLED or FLOW_CONTROL_CTS_ENABLED
    "DSR/DTR" -> FLOW_CONTROL_DSR_ENABLED or FLOW_CONTROL_DTR_ENABLED
    "ON/OFF" -> FLOW_CONTROL_XONXOFF_IN_ENABLED or FLOW_CONTROL_XONXOFF_OUT_ENABLED
    else -> FLOW_CONTROL_DISABLED
}
