package com.y.wtm.data.driver

import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.util.SerialInputOutputManager
import com.y.wtm.TAG
import com.y.wtm.data.HARD
import com.y.wtm.data.SOFT
import com.y.wtm.data.USB_MANAGER
import com.y.wtm.data.UsbMessageListener
import com.y.wtm.data.toHexStrArray
import com.y.wtm.upload.MyMQTT
import com.y.wtm.upload.MyModbus
import com.y.wtm.viewmodel.StateViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DriverUSB(
    private val usbSerialDriver: UsbSerialDriver?,
    private val newBaudRate: Int,
    private val newDataBits: Int,
    private val newStopBits: Int,
    private val newParity: Int,
    private val writeTimeOut: Int = 200
) : Driver {
    private var usbDriver: UsbSerialPort? = null
    @OptIn(DelicateCoroutinesApi::class)
    override fun open() {
        GlobalScope.launch {
            if (usbSerialDriver == null) return@launch
            val device = usbSerialDriver.device
            //没有权限就申请一下
            if (!USB_MANAGER.hasPermission(device)) {
                //请求权限
               // USB_MANAGER.requestPermission(device, pendingIntent)
            } else {
                val openDevice = USB_MANAGER.openDevice(device)
                usbDriver = usbSerialDriver.ports?.get(0)
                usbDriver?.open(openDevice)
                usbDriver?.setParameters(
                    newBaudRate,
                    newDataBits,
                    newStopBits,
                    newParity
                )
                val serialInputOutputManager =
                    SerialInputOutputManager(usbDriver, UsbMessageListener)
                serialInputOutputManager.readTimeout = writeTimeOut
                serialInputOutputManager.start()
                write(SOFT)
                write(HARD)
                Thread.sleep(1000)
                if (StateViewModel.SOFT.isNotEmpty()) {
                    Log.d(TAG, "USBDriver:open: USB初始化完成")
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
        return usbDriver?.isOpen ?: false
    }

    override fun write(bytes: ByteArray) {
        Log.d(TAG, "USBDriver:write: ${bytes.toHexStrArray().contentToString()}")
        usbDriver?.write(bytes, writeTimeOut)
    }

    override fun close() {
        Log.d(TAG, "close: 关闭USB")
        usbDriver?.close()
    }
}