package com.y.wirelesstemperaturemeasurement.data.parse

import android.util.Log
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.data.listener.writeData
import com.y.wirelesstemperaturemeasurement.dataBase
import com.y.wirelesstemperaturemeasurement.room.entity.SensorData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//数据缓存
private val dataBuffer = mutableListOf<Byte>()
//需要回复的数据
private val replyMsg: MutableMap<Long, ByteArray> = mutableMapOf()
fun dataParse(data: ByteArray) {
    data.forEach { dataBuffer.add(it) }
    examineData()
}

private fun examineData() {
    if (dataBuffer.firstOrNull() != 0x53.toByte()) {
        dataBuffer.clear()
        return
    }
    if (dataBuffer.size < MIN_DATA_SIZE) return
    if (dataBuffer.size > dataBuffer[4] + 7) {
        dataBuffer.clear()
        return
    }
    //如果数据符合要求
    if (dataBuffer.size == dataBuffer[4] + 7 && checksum(dataBuffer)) {
        parseData(dataBuffer.toByteArray())
        dataBuffer.clear()
    }
}

private fun parseData(bytes: ByteArray) = runBlocking {
    launch {
        when (bytes[3]) {
            MinorFunctionCode.RW.byte -> readWriteHandler(bytes)
            MinorFunctionCode.TEMP.byte -> tempHandler(bytes)
            MinorFunctionCode.ERROR.byte -> errorHandler(bytes)
            MinorFunctionCode.SUCCESS.byte -> successHandler(bytes)
            MinorFunctionCode.OTHER_DATA.byte -> otherDataHandler(bytes)
            MinorFunctionCode.HARDWARE.byte -> hardwareVersionHandler(bytes)
            MinorFunctionCode.SOFTWARE.byte -> softwareVersionHandler(bytes)
        }
    }
}

fun softwareVersionHandler(bytes: ByteArray) {
    softwareVersion = version(bytes)
    Log.i(TAG, "软件版本 $softwareVersion")
}

fun hardwareVersionHandler(bytes: ByteArray) {
    hardwareVersion = version(bytes)
    Log.i(TAG, "硬件版本: $hardwareVersion")
}

fun readWriteHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 读写数据")
}

fun otherDataHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 其他数据")
}

fun successHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 成功数据")
}

fun errorHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 错误数据")
}

fun tempHandler(msg: ByteArray) {
    val address = address(msg[5], msg[6], msg[7], msg[8])
    if (replyMsg.containsKey(address)) {
        replyMsg[address]?.let { writeData(it) }
    } else {
        val replyData = computeReplyData(msg)
        writeData(replyData)
        replyMsg[address] = replyData
    }

    //TODO  传感器
    val id = dataBase.sensorDao().selectId(address)
    if (id != null) {
        val newTemp = temperature(msg[9], msg[10])
        val newVoltageRH = voltageRH(msg[11], msg[12])
        val sensorHistoryData = SensorData(
            sensorId = id,
            temperature = newTemp,
            voltageRH = newVoltageRH,
            rssi = msg[15]
        )
        Log.i(TAG, "添加历史数据: $sensorHistoryData")
        dataBase.sensorDataDao().addSensorData(sensorHistoryData)
    }
}
