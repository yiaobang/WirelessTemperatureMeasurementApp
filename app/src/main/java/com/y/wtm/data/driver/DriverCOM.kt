package com.y.wtm.data.driver

import android.util.Log
import com.fazecast.jSerialComm.SerialPort
import com.y.wtm.TAG
import com.y.wtm.data.ComMessageListener
import com.y.wtm.data.HARD
import com.y.wtm.data.SOFT
import com.y.wtm.data.flow
import com.y.wtm.data.toHexStrArray
import com.y.wtm.upload.MyMQTT
import com.y.wtm.upload.MyModbus
import com.y.wtm.viewmodel.StateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DriverCOM(
    private var serialPort: SerialPort?,
    private val newBaudRate: Int,
    private val newDataBits: Int,
    private val newStopBits: Int,
    private val newParity: Int,
    private val flowControl: String,
    private val timeOut: Int = 200,
) : Driver {
    override fun open() {
        this.serialPort?.apply {
            Log.i(TAG, "COMDriver:open: 设置参数")
            setComPortParameters(newBaudRate, newDataBits, newStopBits, newParity)
            addDataListener(ComMessageListener)
            setFlowControl(flowControl.flow())
            setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING or SerialPort.TIMEOUT_WRITE_BLOCKING,
                timeOut, timeOut
            )
            if (openPort()) {
                Log.d(TAG, "COMDriver:open: 打开成功")
                write(SOFT)
                write(HARD)
                Thread.sleep(1000)
                if (StateViewModel.SOFT.isNotEmpty()) {
                    Log.d(TAG, "COMDriver:open: 串口初始化完成")
                    //串口连接完成后打开Modbus 和MQTT
                    openAfter()
                } else {
                    close()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun openAfter() {
        GlobalScope.launch {
            MyModbus.init()
            MyMQTT.init()
        }
    }

    override fun isOpen(): Boolean {
        return serialPort?.isOpen ?: false
    }

    override fun write(bytes: ByteArray) {
        Log.d(TAG, "COMDriver:write: ${bytes.toHexStrArray().contentToString()}")
        serialPort?.writeBytes(bytes, bytes.size)
    }

    override fun close() {
        serialPort?.apply {
            removeDataListener()
            closePort()
            Log.d(TAG, "COMDriver:close: 串口关闭")
        }
    }
}