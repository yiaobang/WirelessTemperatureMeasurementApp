package com.y.wirelesstemperaturemeasurement.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Sensor::class,
        parentColumns = ["id"],
        childColumns = ["sensor_id"],
        onDelete = CASCADE
    )],
    tableName = "region",
    indices = [Index(value = ["sensor_id"], unique = true)]
)
data class Region(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "region_name") val regionName: String,
    @ColumnInfo(name = "sensor_id") val sensorId: Int = 0
)