package com.y.wtm.data

import android.util.Log
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions
import com.hoho.android.usbserial.util.SerialInputOutputManager
import com.y.wtm.TAG


/** 消息分隔符 */
const val DELIMITER: Byte = 0x16

/**
 * USB-serialPort 消息监听器
 * @author Y
 * @date 2024/03/01
 */
object UsbMessageListener : SerialInputOutputManager.Listener {
    /**  Usb消息缓存*/
    private val usbMessageBuffer = mutableListOf<Byte>()
    override fun onNewData(p0: ByteArray) {
        Log.d(TAG, "UsbMessageListener:onNewData: ${p0.toHexStrArray().contentToString()}")
        p0.forEach { messageProcessing(it) }
    }

    override fun onRunError(p0: Exception?) {
        Log.e(TAG, "UsbMessageListener:onRunError: ", p0)
    }
}

/**
 * 串口消息监听器
 * @author Y
 * @date 2023/12/31
 */

object ComMessageListener : SerialPortMessageListenerWithExceptions {
    /**
     * 获取侦听事件
     * @return [Int]
     */
    override fun getListeningEvents(): Int {
        //数据接收的监听事件
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED
    }

    /**
     * 触发串口事件
     * @param [event] 事件
     */
    override fun serialEvent(event: SerialPortEvent) {
        val receivedData = event.receivedData
        Log.d(TAG, "serialEvent: ${receivedData.toHexStrArray().contentToString()}")
        messageProcessing(receivedData)
    }

    /**
     * 获取消息分隔符
     * @return [ByteArray]
     */
    override fun getMessageDelimiter(): ByteArray {
        return byteArrayOf(DELIMITER)
    }

    /**
     * 分隔符表示消息结束
     * @return [Boolean]
     */
    override fun delimiterIndicatesEndOfMessage(): Boolean {
        return true
    }

    /**
     * 捕获异常
     * @param [e] E
     */
    override fun catchException(e: Exception?) {
        Log.e(TAG, "读取串口数据发生异常: ", e)
    }
}
