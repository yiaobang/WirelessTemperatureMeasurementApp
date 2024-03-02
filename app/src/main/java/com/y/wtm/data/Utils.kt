package com.y.wtm.data

import com.fazecast.jSerialComm.SerialPort

/**
 * 转换为十六进制字符串数组
 * @return [Array<String>]
 */
fun ByteArray.toHexStrArray(): Array<String> = this.map { byte -> "%02X".format(byte) }.toTypedArray()
/**
 * 校验和的结果
 */
fun checkSum(data: MutableList<Byte>): Boolean = calculateChecksumResult(data.dropLast(2).toByteArray()) == (data[data.size - 2].toInt() and 0xFF).toByte()
/**
 * 计算校验和
 */
fun calculateChecksumResult(data: ByteArray): Byte = (data.sum() and 0xFF).toByte()
/**
 * 计算需要返回的byte数组
 * @return
 */
fun computeReplyData(data: ByteArray): ByteArray {
    val byteArrayOf = byteArrayOf(
        0x53,
        data[1],
        0x03,
        0x04,
        0x04,
        data[5],
        data[6],
        data[7],
        data[8]
    )
    return byteArrayOf + calculateChecksumResult(byteArrayOf) + 0x16
}

fun Int.baudRate() = this.toString()
fun Int.dataBits() = this.toString()
fun Int.stopBits() = when (this) {
    SerialPort.ONE_POINT_FIVE_STOP_BITS -> "1.5"
    SerialPort.TWO_STOP_BITS -> "2"
    else -> "1"
}

fun Int.parity() = when (this) {
    SerialPort.ODD_PARITY -> "奇校验"
    SerialPort.EVEN_PARITY -> "偶校验"
    SerialPort.MARK_PARITY -> "标记位校验"
    SerialPort.SPACE_PARITY -> "空格校验"
    else -> "无"
}

fun Int.flow() = when (this) {
    SerialPort.FLOW_CONTROL_RTS_ENABLED or SerialPort.FLOW_CONTROL_CTS_ENABLED -> "RTS/CTS"
    SerialPort.FLOW_CONTROL_DSR_ENABLED or SerialPort.FLOW_CONTROL_DTR_ENABLED -> "DSR/DTR"
    SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED or SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED -> "ON/OFF"
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
    "1.5" -> SerialPort.ONE_POINT_FIVE_STOP_BITS
    "2" -> SerialPort.TWO_STOP_BITS
    else -> SerialPort.ONE_STOP_BIT
}

fun String.parity() = when (this) {
    "奇校验" -> SerialPort.ODD_PARITY
    "偶校验" -> SerialPort.EVEN_PARITY
    "标记位校验" -> SerialPort.MARK_PARITY
    "空格校验" -> SerialPort.SPACE_PARITY
    else -> SerialPort.NO_PARITY
}

fun String.flow() = when (this) {
    "RTS/CTS" -> SerialPort.FLOW_CONTROL_RTS_ENABLED or SerialPort.FLOW_CONTROL_CTS_ENABLED
    "DSR/DTR" -> SerialPort.FLOW_CONTROL_DSR_ENABLED or SerialPort.FLOW_CONTROL_DTR_ENABLED
    "ON/OFF" -> SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED or SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED
    else -> SerialPort.FLOW_CONTROL_DISABLED
}
