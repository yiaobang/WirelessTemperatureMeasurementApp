package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.y.wirelesstemperaturemeasurement.room.entity.Sensor

@Dao
interface SensorDao {
    @Insert
    fun addSensor(vararg sensor: Sensor)
    @Update
    fun updateSensor(vararg sensor: Sensor)
    @Delete
    fun deleteSensor(vararg sensor: Sensor)
    @Query("SELECT * FROM sensor")
    fun selectSensors(): List<Sensor>
    @Query("SELECT id FROM sensor WHERE serial_number =:serialNumber")
    fun selectId(serialNumber: Long): Int?
}