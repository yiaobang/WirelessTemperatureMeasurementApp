package com.y.wtm.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.y.wtm.room.ShowEvent

@Dao
interface ShowEventDao {
    /**
     * 查询所有事件
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id ORDER BY d.time DESC"
    )
    suspend fun allEvent(): List<ShowEvent>

    /**
     * 根据时间过滤事件
     * @param [start] 开始时间
     * @param [end] 结束时间
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(start: Long, end: Long): List<ShowEvent>

    /**
     * 根据时间级别和时间过滤事件
     *
     * @param eventLevel 事件级别
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE e.event_level=:eventLevel AND d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(eventLevel: Int, start: Long, end: Long): List<ShowEvent>

    /**
     * 根据设备名称和时间过滤
     * @param [deviceName] 设备名称
     * @param [start]开始时间
     * @param [end]结束时间
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE p.device_name=:deviceName AND d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(deviceName: String, start: Long, end: Long): List<ShowEvent>

    /**
     * 根据设备名称事件级别时间过滤
     * @param [deviceName]设备名称
     * @param [eventLevel]事件级别
     * @param [start]开始时间
     * @param [end]结束时间
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE p.device_name=:deviceName AND e.event_level=:eventLevel AND d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(
        deviceName: String,
        eventLevel: Int,
        start: Long, end: Long
    ): List<ShowEvent>

    /**
     * 根据id和时间过滤
     * @param [id]
     * @param [start]
     * @param [end]
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE p.parts_id=:id  AND d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(id: Long, start: Long, end: Long): List<ShowEvent>

    /**
     * 根据ID 级别和时间过滤
     * @param [id]
     * @param [eventLevel]
     * @param [start]
     * @param [end]
     * @return [List<ShowEvent>]
     */
    @Query(
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,e.event_level,e.event_msg,d.time " +
                "FROM TEMPERATURE_MEASURING_POINT_DATA AS d " +
                "JOIN temperature_measuring_point AS p ON d.parts_id=p.parts_id " +
                "JOIN temperature_measuring_point_event AS e ON  d.id=e.data_id " +
                "WHERE p.parts_id=:id AND e.event_level=:eventLevel  AND d.time>=:start AND d.time<=:end ORDER BY d.time DESC"
    )
    suspend fun showEvent(
        id: Long,
        eventLevel: Int,
        start: Long, end: Long
    ): List<ShowEvent>
}