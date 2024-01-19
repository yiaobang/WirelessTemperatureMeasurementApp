package com.y.wirelesstemperaturemeasurement.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "sensor", indices = [Index(value = ["serial_number"], unique = true)])
data class Sensor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "serial_number") val serialNumber: Long,
    @ColumnInfo(name = "sensor_type") val type: Byte
)