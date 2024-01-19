package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.y.wirelesstemperaturemeasurement.room.entity.SensorEvent

@Dao
interface SensorEventDao {
    @Insert
    fun addSensorEvent(vararg sensorEvent: SensorEvent)

    @Update
    fun updateSensorEvent(vararg sensorEvent: SensorEvent)

    @Delete
    fun deleteSensorEvent(vararg sensorEvent: SensorEvent)

    @Query("SELECT * FROM sensor_event")
    fun selectSensorEvents(): List<SensorEvent>

    @Query("DELETE FROM sensor_event WHERE time < :time")
    fun deleteOldEvent(time: Long)
}