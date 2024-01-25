package com.y.wirelesstemperaturemeasurement.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[PartsDao]
 * 查询测温点
 */
@Dao
interface PartsDao {
    @Insert
    fun insert(vararg parts: Parts)

    /**
     * 更换传感器
     * @param [oldSerialNumber] 旧的传感器序列号
     * @param [newSerialNumber] 新的传感器序列号
     * @param [type] 新的传感器类型
     */
    @Query("UPDATE temperature_measuring_point SET serial_number = :newSerialNumber, sensor_type = :type WHERE serial_number = :oldSerialNumber ")
    fun updateParts(oldSerialNumber:Int,newSerialNumber:Int,type:Byte)

    /**
     * 按照传感器删除测温点
     * @param [serialNumber] 传感器序列号
     */
    @Query("DELETE FROM temperature_measuring_point WHERE serial_number=:serialNumber")
    fun delete(serialNumber:Int)

    /**
     * 根据传感器序列号查询是否存在测温点
     * @param [serialNumber] 传感器序列号
     * @return [Int?]
     */
    @Query("SELECT id FROM temperature_measuring_point WHERE serial_number = :serialNumber")
    fun selectById(serialNumber: Int):Int?

    /**
     * 查询所有测温点
     * @return [List<Parts>]
     */
    @Query("SELECT * FROM temperature_measuring_point")
    fun selectAllParts():List<Parts>
}

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[DataDao]
 * 查询数据
 */
@Dao
interface DataDao {
    @Insert
    fun insert(vararg data: Data)
    fun deleteOldData() = deleteOldData(deleteTime())
    @Query("SELECT * FROM temperature_measuring_point_data")
    fun select():List<Data>

    @Query("DELETE FROM temperature_measuring_point_data WHERE time < :time")
    fun deleteOldData(time: Long)
}

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[EventDao]
 * 查询事件
 */
@Dao
interface EventDao {
    @Insert
    fun insert(vararg event: Event)
    fun deleteOldEvent() = deleteOldEvent(deleteTime())

    @Query("DELETE FROM temperature_measuring_point_event WHERE time < :time")
    fun deleteOldEvent(time: Long)
}

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[JointDao]
 * 联合查询
 */
@Dao
interface JointDao {
    /**
     * 查询每个测温点的最新数据
     * @return [List<DataShow>]
     */
    @Query(
        "SELECT p.id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.rssi,d.event_level,MAX(d.time) as time" +
                " FROM temperature_measuring_point AS p JOIN temperature_measuring_point_data AS d " +
                "ON p.id=d.parts_id " +
                "GROUP BY d.parts_id"
    )
    fun dataShow(): List<DataShow>
}

private fun deleteTime() = System.currentTimeMillis() - 86_400_000