package com.y.wtm.data

import android.util.Log
import com.y.wtm.TAG
import com.y.wtm.viewmodel.RoomViewModel
import com.y.wtm.viewmodel.StateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//需要回复的数据缓存
private val replyMessage: MutableMap<Long, ByteArray> = mutableMapOf()

//数据缓存
private val dataBuffer = mutableListOf<Byte>()

//计算测温间隔
val timeSum: MutableMap<Long, Long> = mutableMapOf()

fun messageProcessing(byte: Byte) {
    dataBuffer.add(byte)
    if (byte == DELIMITER) {
        messageProcessing()
    }
}

fun messageProcessing(data: ByteArray) {
    dataBuffer.addAll(data.asList())
    messageProcessing()
}

/**
 * 消息检查
 *
 */
fun messageProcessing() {
    Log.d(TAG, "MessageHandler:${dataBuffer.toByteArray().toHexStrArray().contentToString()}")
    //检查数据长度
    if (dataBuffer.size < MIN_MESSAGE_SIZE || dataBuffer.size < dataBuffer[4] + 7) return
    //校验数据开头结尾和数据长度
    if (dataBuffer.firstOrNull() != 0x53.toByte() || dataBuffer.lastOrNull() != 0x16.toByte() || dataBuffer.size > dataBuffer[4] + 7) {
        dataBuffer.clear()
        return
    }
    //检查校验和
    if (checkSum(dataBuffer)) {
        messageDistribute(dataBuffer.toByteArray())
    }
    dataBuffer.clear()
}

@OptIn(DelicateCoroutinesApi::class)
private fun messageDistribute(bytes: ByteArray) = GlobalScope.launch {
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
    val serialNumber = address(msg[5], msg[6], msg[7], msg[8]).toLong()
    if (!replyMessage.containsKey(serialNumber)) {
        replyMessage[serialNumber] = computeReplyData(msg)
    }
    replyMessage[serialNumber]?.let { write(it) }
    val lastTime = timeSum[serialNumber] ?: 0
    Log.e(TAG, "$serialNumber : ${(System.currentTimeMillis() - lastTime) / 1000}秒")
    timeSum[serialNumber] = System.currentTimeMillis()
    RoomViewModel.addPartsData(serialNumber, msg)
}