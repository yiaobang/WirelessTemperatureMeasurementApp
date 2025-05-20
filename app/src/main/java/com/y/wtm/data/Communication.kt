package com.y.wtm.data

import android.hardware.usb.UsbManager
import com.fazecast.jSerialComm.SerialPort
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.y.wtm.config.Config
import com.y.wtm.data.driver.Driver
import com.y.wtm.data.driver.DriverCOM
import com.y.wtm.data.driver.DriverType
import com.y.wtm.data.driver.DriverUSB
import com.y.wtm.viewmodel.StateViewModel

val serialPorts: Array<SerialPort> by lazy { SerialPort.getCommPorts() }
lateinit var USB_MANAGER: UsbManager
val usbSerialDrivers: List<UsbSerialDriver> by lazy {
    UsbSerialProber.getDefaultProber().findAllDrivers(USB_MANAGER)
}

@Volatile
private var driver: Driver? = null
private var driverType: DriverType = DriverType.driverType("COM")
private var serialNumber: String = ""
private var baudRate: Int = 19200
private var dataBits: Int = 8
private var stopBits: Int = 1
private var parity: Int = 0
private var flow: String = "NONE"
private fun updateParameter() {
    val type = Config.readConfig("通信方式", "COM")
    driverType = DriverType.driverType(type)
    serialNumber = Config.readConfig(type, "")
    baudRate = Config.readConfig("baudRate", "19200").baudRate()
    dataBits = Config.readConfig("dataBits", "8").dataBits()
    stopBits = Config.readConfig("stopBits", "1").stopBits()
    parity = Config.readConfig("parity", "0").parity()
    flow = Config.readConfig("flow", "NONE")
}

fun initDriver() {
    StateViewModel.SOFT = ""
    StateViewModel.HARD = ""
    driverClose()
    updateParameter()
    println(driverType)
    when (driverType) {
        DriverType.COM -> {
            driver = DriverCOM(
                serialPorts.firstOrNull { serialNumber == it.systemPortName },
                baudRate,
                dataBits,
                stopBits,
                parity,
                flow
            )
        }

        DriverType.USB -> {
            driver = DriverUSB(
                usbSerialDrivers.firstOrNull { serialNumber == it.device.serialNumber },
                baudRate,
                dataBits,
                stopBits,
                parity,
            )
        }
    }
    driver?.open()
}

fun write(bytes: ByteArray) {
    driver?.write(bytes)
}

fun driverClose() {
    driver?.close()
}


