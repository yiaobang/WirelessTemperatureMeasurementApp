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
import java.util.Timer
import java.util.TimerTask

object MyModbus {
    @Volatile
    private lateinit var master: ModbusTCPMaster

    //正在连接
    @Volatile
    private var conning: Boolean = false
    private var address = "127.0.0.1"
    private var port = 502
    private val timer = Timer()
    /**
     * Modbus连接任务
     */
    private val timerTask = object : TimerTask() {
        override fun run() {
            try {
                master.connect()
                if (master.isConnected) {
                    Log.d(TAG, "run: Modbus连接成功")
                    //初始化完成关闭任务
                    conning = false
                    timer.cancel()
                }
            } catch (e: Exception) {
                Log.e(TAG, "run: Modbus连接异常 ${e.message}")
            }
        }
    }
    private fun read() {
        address = Config.readConfig("modbus-IP", address)
        port = Config.readConfig("modbus-port", "502").toInt()
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun init() = GlobalScope.launch {
        read()
        timer.cancel()
        conning = true
        if (::master.isInitialized && master.isConnected) {
            master.disconnect()
        }
        master = ModbusTCPMaster(address, port)
        Log.d(TAG, "init: Modbus开始初始化")
        //启动任务
        timer.schedule(timerTask, 0, 60_000)
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
                        conning =true
                        timer.schedule(timerTask, 0, 60_000)
                    }
                }
            } else {
                if (!conning) {
                    conning =true
                    timer.schedule(timerTask, 0, 60_000)
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