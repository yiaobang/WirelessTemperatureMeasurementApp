package com.y.wtm.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.y.wtm.room.HistoryData

@Dao
interface HistoryDataDao {
    /**
     * 查询所有历史数据
     * @return [List<HistoryData>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.time " +
                "FROM temperature_measuring_point AS p " +
                "JOIN temperature_measuring_point_data  AS d ON p.parts_id = d.parts_id ORDER BY d.time DESC"
    )
    suspend fun allHistoryData(): List<HistoryData>


    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.time " +
                "FROM temperature_measuring_point AS p " +
                "JOIN temperature_measuring_point_data  AS d ON p.parts_id = d.parts_id " +
                "WHERE p.parts_id=:id AND d.time >=:start AND d.time <=:end ORDER BY d.time DESC"
    )
    suspend fun historyData(
        id: Long,
        start: Long,
        end: Long
    ): List<HistoryData>

    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.time " +
                "FROM temperature_measuring_point AS p " +
                "JOIN temperature_measuring_point_data  AS d ON p.parts_id = d.parts_id " +
                "WHERE p.device_name=:deviceName AND d.time >=:start AND d.time <=:end ORDER BY d.time DESC"
    )
    suspend fun historyData(
        deviceName: String,
        start: Long,
        end: Long
    ): List<HistoryData>
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.time " +
                "FROM temperature_measuring_point AS p " +
                "JOIN temperature_measuring_point_data  AS d ON p.parts_id = d.parts_id " +
                "WHERE  d.time >=:start AND d.time <=:end ORDER BY d.time DESC"
    )
    suspend fun historyData(
        start: Long,
        end: Long
    ): List<HistoryData>
}