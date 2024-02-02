package com.y.wirelesstemperaturemeasurement.upload

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster
import com.ghgande.j2mod.modbus.procimg.SimpleRegister
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.config.Config
import com.y.wirelesstemperaturemeasurement.room.Data
import com.y.wirelesstemperaturemeasurement.room.toShorts
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MyModbus {
    private lateinit var master: ModbusTCPMaster

    //正在连接
    @Volatile
    private var conning: Boolean = false
    private val handler = Handler(Looper.getMainLooper())
    private var address = "127.0.0.1"
    private var port = 502
    private fun read() {
        address = Config.readConfig("modbus-IP", address)
        port = Config.readConfig("modbus-port", "502").toInt()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init() {
        read()
        if (::master.isInitialized) {
            handler.removeCallbacks(connModbus)
            GlobalScope.launch {
                master.disconnect()
                Log.d(TAG, "run: Modbus断开成功")
            }
        }
        master = ModbusTCPMaster(address, port)
        handler.post(connModbus)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val connModbus = object : Thread() {
        override fun run() {
            conning = true
            try {
                GlobalScope.launch {
                    master.connect()
                    Log.d(TAG, "run: Modbus连接成功")
                }
            } catch (e: Exception) {
                Log.e(TAG, "run: Modbus连接异常", e)
            }
            if (master.isConnected) {
                handler.removeCallbacks(this)
                conning = false
            } else {
                handler.postDelayed(this, 120000)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateMultipleRegisters(id: Int, data: Array<SimpleRegister>) {
        GlobalScope.launch {
            if (master.isConnected) {
                try {
                    master.writeMultipleRegisters(id, 0, data)
                } catch (e: Exception) {
                    Log.e(TAG, "updateMultipleRegisters: Modbus数据写入异常", e)
                }
            } else {
                if (!conning) {
                    handler.post(connModbus)
                }
            }
        }

    }

    fun updateMultipleRegisters(id: Int, type: Int, newData: Data, eventLevel: Int) {
        val toShorts = newData.time.toShorts()
        val data = arrayOf(
            SimpleRegister(type),
            SimpleRegister(newData.temperature),
            SimpleRegister(newData.voltageRH),
            SimpleRegister(eventLevel),
            SimpleRegister(toShorts[0].toInt()),
            SimpleRegister(toShorts[1].toInt()),
            SimpleRegister(toShorts[2].toInt()),
            SimpleRegister(toShorts[3].toInt())
        )
        updateMultipleRegisters(id, data)
    }
}