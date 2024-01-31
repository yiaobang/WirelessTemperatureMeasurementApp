package com.y.wirelesstemperaturemeasurement.upload

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster
import com.ghgande.j2mod.modbus.procimg.SimpleRegister

object MyModbus {
    private lateinit var master: ModbusTCPMaster
    private var connect:Boolean = false
    fun init(address: String, port: Int = 502) {
        master = ModbusTCPMaster(address, port)
        master.connect()
    }

    fun updateMultipleRegisters(id: Int, data: Array<SimpleRegister>) {
        master.writeMultipleRegisters(id, 0, data)
    }
}