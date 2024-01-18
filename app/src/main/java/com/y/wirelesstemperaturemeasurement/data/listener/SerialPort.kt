package com.y.wirelesstemperaturemeasurement.data.listener

import android.util.Log
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.data.parse.dataParse
import com.y.wirelesstemperaturemeasurement.data.parse.toHexStrArray

@Volatile
private lateinit var serialPort: SerialPort
const val baudRate: Int = 115200
const val dataBits: Int = 8
const val stopBits: Int = SerialPort.ONE_STOP_BIT
const val parity: Int = SerialPort.NO_PARITY
fun writeData(bytes: ByteArray) {
    serialPort.writeBytes(bytes, bytes.size)
}
/**
 * 串口消息监听器
 * @author Y
 * @date 2023/12/31
 */

object SerialPortMessageListener : SerialPortMessageListenerWithExceptions {
    private const val DELIMITER: Byte = 0x16

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
        dataParse(receivedData)
        Log.d(TAG, "serialEvent: ${receivedData.toHexStrArray().contentToString()}")
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