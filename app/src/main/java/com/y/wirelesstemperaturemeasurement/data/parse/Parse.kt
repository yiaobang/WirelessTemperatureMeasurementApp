package com.y.wirelesstemperaturemeasurement.data.parse

import android.util.Log
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.data.listener.writeData
import com.y.wirelesstemperaturemeasurement.dataBase
import com.y.wirelesstemperaturemeasurement.room.entity.SensorData
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//需要回复的数据
private val replyMsg: MutableMap<Int, ByteArray> = mutableMapOf()

val timeSum: MutableMap<Int, Long> = mutableMapOf()
//数据缓存
private val dataBuffer = mutableListOf<Byte>()
fun dataParse(data: ByteArray) {
    dataBuffer.addAll(data.asList())
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
        Log.w(TAG, "传递,不在阻塞串口线程")
    }
}

@OptIn(DelicateCoroutinesApi::class)
private fun parseData(bytes: ByteArray) = GlobalScope.launch {
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

private fun softwareVersionHandler(bytes: ByteArray) {
    StateViewModel.SOFT = version(bytes)
    Log.i(TAG, "软件版本 ${StateViewModel.SOFT}")
}

private fun hardwareVersionHandler(bytes: ByteArray) {
    StateViewModel.HARD = version(bytes)
    Log.i(TAG, "硬件版本: ${StateViewModel.HARD}")
}

private fun readWriteHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 读写数据")
}

private fun otherDataHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 其他数据")
}

private fun successHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 成功数据")
}

private fun errorHandler(bytes: ByteArray) {
    Log.i(TAG, "otherDataHandler: 错误数据")
}

private fun tempHandler(msg: ByteArray) {
    val serialNumber = address(msg[5], msg[6], msg[7], msg[8])
    if (!replyMsg.containsKey(serialNumber)) {
        replyMsg[serialNumber] = computeReplyData(msg)
    }
    replyMsg[serialNumber]?.let { writeData(it) }
    Log.e(TAG, "$serialNumber : ${(System.currentTimeMillis()- timeSum[serialNumber]!!)/1000}秒")
    timeSum[serialNumber] = System.currentTimeMillis()
    //TODO  传感器
    val id = dataBase.sensorDao().selectId(serialNumber)
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
