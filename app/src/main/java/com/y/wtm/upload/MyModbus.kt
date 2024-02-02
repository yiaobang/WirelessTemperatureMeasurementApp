package com.y.wtm.upload

import android.util.Log
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster
import com.ghgande.j2mod.modbus.procimg.SimpleRegister
import com.y.wtm.TAG
import com.y.wtm.config.Config
import com.y.wtm.room.Data
import com.y.wtm.room.toShorts
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

object MyModbus {
    private lateinit var master: ModbusTCPMaster

    //正在连接
    @Volatile
    private var conning: Boolean = false
    private var address = "127.0.0.1"
    private var port = 502
    private fun read() {
        address = Config.readConfig("modbus-IP", address)
        port = Config.readConfig("modbus-port", "502").toInt()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init() = GlobalScope.launch {
        read()
        if (::master.isInitialized) {
            master.disconnect()
        }
        master = ModbusTCPMaster(address, port)
        Log.d(TAG, "init: Modbus开始初始化")
        conn()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun conn() = GlobalScope.launch {
        conning = true
        while (conning) {
            try {
                master.connect()
                if (master.isConnected) {
                    Log.d(TAG, "run: Modbus连接成功")
                    conning = false
                }
            } catch (e: Exception) {
                Log.e(TAG, "run: Modbus连接异常 ${e.message}")
            }
            sleep(60000)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateMultipleRegisters(id: Int, data: Array<SimpleRegister>) {
        GlobalScope.launch {
            if (master.isConnected) {
                try {
                    master.writeMultipleRegisters(id, 0, data)
                    Log.d(TAG, "Modbus写入寄存器${data.contentToString()}")
                } catch (e: Exception) {
                    Log.e(TAG, "updateMultipleRegisters: Modbus数据写入异常", e)
                    if (!conning) {
                        conn()
                    }
                }
            } else {
                if (!conning) {
                    conn()
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