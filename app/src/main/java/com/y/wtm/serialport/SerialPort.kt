package com.y.wtm.serialport

import android.util.Log
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions
import com.y.wtm.TAG
import com.y.wtm.config.Config.readConfig
import com.y.wtm.upload.MyMQTT
import com.y.wtm.upload.MyModbus
import com.y.wtm.viewmodel.StateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val serialPorts: Array<SerialPort> = SerialPort.getCommPorts()
val serialPortNames = serialPorts.map { it.systemPortName }

@Volatile
private var serialPort: SerialPort? = null
private var serialNumberY: String = ""
private var baudRateY: Int = 19200
private var dataBitsY: Int = 8
private var stopBitsY: Int = 1
private var parityY: Int = 0
private var flowY: Int = 0
private fun read() {
    serialNumberY = readConfig("串口号")
    baudRateY = readConfig("波特率", "19200").baudRate()
    dataBitsY = readConfig("数据位", "8").dataBits()
    stopBitsY = readConfig("停止位", "1").stopBits()
    parityY = readConfig("校验", "0").parity()
    flowY = readConfig("流控", "0").flow()
}

@OptIn(DelicateCoroutinesApi::class)
fun connection() {
    StateViewModel.SOFT = ""
    StateViewModel.HARD = ""
    read()
    disConnection()
    Log.d(
        TAG,
        "connection: 串口号: $serialNumberY 波特率: $baudRateY 数据位: $dataBitsY " +
                "停止位: $stopBitsY 校验: $parityY 流控: $flowY"
    )
    serialPort = serialPorts
        .firstOrNull { serialNumberY == it.systemPortName }
    serialPort?.apply {
        Log.i(TAG, "connection: 设置参数")
        setComPortParameters(baudRateY, dataBitsY, stopBitsY, parityY)
        addDataListener(SerialPortMessageListener)
        setFlowControl(flowY)
        setComPortTimeouts(
            SerialPort.TIMEOUT_READ_BLOCKING or SerialPort.TIMEOUT_WRITE_BLOCKING,
            100, 100
        )
        if (openPort()) {
            Log.d(TAG, "connection: 打开成功")
            writeData(SOFT)
            writeData(HARD)
            Thread.sleep(500)
            if (StateViewModel.SOFT.isNotEmpty()) {
                Log.d(TAG, "connection: 串口初始化完成")
                //串口连接完成后打开Modbus 和MQTT
                GlobalScope.launch{
                    MyModbus.init()
                    MyMQTT.init()
                }
            } else {
                removeDataListener()
                closePort()
            }
        }
    }
}

fun disConnection() {
    serialPort?.apply {
        removeDataListener()
        closePort()
        Log.d(TAG, "disConnection: 串口关闭")
    }
}

fun writeData(bytes: ByteArray) {
    Log.d(TAG, "writeData: ${bytes.toHexStrArray().contentToString()}")
    serialPort?.writeBytes(bytes, bytes.size)
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
        Log.d(TAG, "serialEvent: ${receivedData.toHexStrArray().contentToString()}")
        dataParse(receivedData)
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