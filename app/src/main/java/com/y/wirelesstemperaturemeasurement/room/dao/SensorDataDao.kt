package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.y.wirelesstemperaturemeasurement.room.entity.SensorData

@Dao
interface SensorDataDao {
    @Insert
    fun addSensorData(vararg sensorHistoryData: SensorData)
    @Delete
    fun deleteSensorData(vararg sensorHistoryData: SensorData)
    @Query("SELECT * FROM sensor_data ORDER BY time DESC")
    fun selectSensorDatas(): List<SensorData>
    @Query("DELETE FROM SENSOR_DATA WHERE time < :time")
    fun deleteOldData(time: Long)
}