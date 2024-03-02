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
    @Volatile
    private lateinit var master: ModbusTCPMaster

    //正在连接
    @Volatile
    private var conning: Boolean = false

    @Volatile
    private var address = "127.0.0.1"

    @Volatile
    private var port = 502

    @OptIn(DelicateCoroutinesApi::class)
    private fun connModbus() = GlobalScope.launch {
        conning = true
        while (conning) {
            try {
                master = ModbusTCPMaster(address, port)
                master.connect()
                conning = !master.isConnected
            } catch (e: Exception) {
                Log.e(TAG, "connModbus: ${e.message}")
            }
            sleep(60_000)
        }
    }

    private fun read() {
        address = Config.readConfig("modbus-IP", address)
        port = Config.readConfig("modbus-port", "502").toInt()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init() = GlobalScope.launch {
        read()
        if (::master.isInitialized && master.isConnected) {
            master.disconnect()
        }
        Log.d(TAG, "init: Modbus开始初始化")
        if (!conning) {
            //启动任务
            connModbus()
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun updateMultipleRegisters(id: Int, data: Array<SimpleRegister>) {
        GlobalScope.launch {
            if (master.isConnected) {
                try {
                    master.writeMultipleRegisters(id, 0, data)
                    Log.d(TAG, "Modbus写入寄存器id=$id [${data.contentToString()}]")
                } catch (e: Exception) {
                    Log.e(TAG, "updateMultipleRegisters: Modbus数据写入异常", e)
                    if (!conning) {
                        //启动任务
                        connModbus()
                    }
                }
            } else {
                if (!conning) {
                    //启动任务
                    connModbus()
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