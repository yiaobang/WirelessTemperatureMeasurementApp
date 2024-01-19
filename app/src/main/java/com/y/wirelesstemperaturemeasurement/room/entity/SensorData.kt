package com.y.wirelesstemperaturemeasurement.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Sensor::class,
        parentColumns = ["id"],
        childColumns = ["sensor_id"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "sensor_data",
    indices = [Index(value = ["id"], unique = true), Index(value = ["sensor_id"])]
)
data class SensorData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "sensor_id") val sensorId: Int = 0,
    @ColumnInfo(name = "event_level") val eventLevel: Byte = 0,
    @ColumnInfo(name = "temperature") val temperature: Double = 0.0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Int = 0,
    @ColumnInfo(name = "rssi") val rssi: Byte = -70,
    @ColumnInfo(name = "time") val time: Long = System.currentTimeMillis()
)