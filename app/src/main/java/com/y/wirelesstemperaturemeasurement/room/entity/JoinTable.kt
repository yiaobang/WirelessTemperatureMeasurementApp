package com.y.wirelesstemperaturemeasurement.room.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ShowData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "region_name") val regionName: String,
    @ColumnInfo(name = "temperature") val temperature: Double = 0.0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Int = 0,
    @ColumnInfo(name = "rssi") val rssi: Byte = -70,
    @ColumnInfo(name = "sensor_type") val type: Byte,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "event_level") val eventLevel:Byte
)

data class Device(
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "region_name") val regionName: String,
    @ColumnInfo(name = "serial_number") val serialNumber: Long,
    @ColumnInfo(name = "sensor_type") val type: Byte,
    @ColumnInfo(name = "sensor_id") val id: Long = 0
)

