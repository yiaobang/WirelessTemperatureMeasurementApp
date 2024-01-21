package com.y.wirelesstemperaturemeasurement.config

import android.content.Context
import android.content.SharedPreferences
import com.fazecast.jSerialComm.SerialPort

const val config_name = "WirelessTemperatureMeasurementAppConfig"

@SuppressWarnings("all")
object Config {
    private lateinit var context: Context
    private lateinit var read: SharedPreferences
    private lateinit var write: SharedPreferences.Editor

    //Listen
    @Volatile
    var serialNumber: String? = null

    @Volatile
    var baudRate: Int = 115200

    @Volatile
    var dataBits: Int = 8

    @Volatile
    var stopBits: Int = SerialPort.ONE_STOP_BIT

    @Volatile
    var parity: Int = SerialPort.NO_PARITY

    @Volatile
    var flow: Int = SerialPort.FLOW_CONTROL_DISABLED


    //UpLoad
    @Volatile
    var mqtt_address: String? = null

    @Volatile
    var mqtt_port: Int = 1883

    @Volatile
    var mqtt_username: String? = null

    @Volatile
    var mqtt_password: String? = null


    fun initialize(context: Context) {
        this.context = context
        read = Config.context.getSharedPreferences(config_name, Context.MODE_PRIVATE)
        write = read.edit()
    }

    private fun initSerialNumber() {
        serialNumber = read.getString("serialNumber", null);
        baudRate = read.getInt("baudRate", 19200)
        dataBits = read.getInt("dataBits", 8)
        stopBits = read.getInt("stopBits", 1)
        parity = read.getInt("parity", 0)
        flow = read.getInt("flow", 0)
    }

    fun updateSerialNumber(
        serialNumber: String,
        baudRate: Int,
        dataBits: Int,
        stopBits: Int,
        parity: Int,
        flow: Int
    ) {
        write.putString("serialNumber", serialNumber)
        write.putInt("baudRate", baudRate)
        write.putInt("dataBits", dataBits)
        write.putInt("stopBits", stopBits)
        write.putInt("parity", parity)
        write.putInt("flow", flow)
        write.apply()
    }
    private fun initMQTT() {
        read.getString("address", null)
        read.getInt("port", 1883)
        read.getString("username", null)
        read.getString("password", null)
    }
    fun updateMQTT(address: String, port: Int, username: String, password: String) {
        write.putString("address", address)
        write.putInt("port", port)
        write.putString("username", username)
        write.putString("password", password)
        write.apply()
    }

    private fun initModbus() {

    }


}