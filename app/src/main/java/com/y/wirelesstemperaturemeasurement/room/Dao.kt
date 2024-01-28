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
    suspend fun insert(vararg parts: Parts)



    /**
     * 更新测温点
     * @param [id] 原始ID
     * @param [newId] 新的ID
     * @param [deviceName] 新的设备名称
     * @param [partsName] 新的测温点名称
     * @param [serialNumber] 新的序列号
     * @param [type] 新的传感器类型
     */
    @Query(
        "UPDATE temperature_measuring_point " +
                "SET parts_id=:newId," +
                "device_name=:deviceName," +
                "parts_name=:partsName," +
                "serial_number=:serialNumber," +
                "sensor_type=:type " +
                "WHERE parts_id=:id"
    )
    suspend fun update(
        id: Int,
        newId: Int,
        deviceName: String,
        partsName: String,
        serialNumber: Long,
        type: Byte
    )

    @Query("SELECT * FROM temperature_measuring_point WHERE parts_id = :id")
    suspend fun selectId(id: Int): Parts?
    @Query("DELETE FROM temperature_measuring_point WHERE parts_id = :id")
    suspend fun deleteId(id:Int)
    @Query("SELECT * FROM temperature_measuring_point WHERE serial_number=:serialNumber")
    suspend fun selectSensor(serialNumber: Long): Parts?


    @Query("SELECT * FROM temperature_measuring_point WHERE parts_id=:id OR (device_name=:deviceName AND parts_name=:partsName) OR serial_number=:serialNumber")
    suspend fun selectParts(
        id: Int,
        deviceName: String,
        partsName: String,
        serialNumber: Long
    ): List<Parts>?

    /**
     * 根据传感器序列号查询是否存在测温点
     * @param [serialNumber] 传感器序列号
     * @return [Int?]
     */
    @Query("SELECT parts_id FROM temperature_measuring_point WHERE serial_number = :serialNumber")
    suspend fun selectById(serialNumber: Long): Int?

    @Query("SELECT parts_id FROM temperature_measuring_point WHERE device_name = :deviceName AND parts_name = :partsName")
    suspend fun selectByDeviceNameAndPartsName(deviceName: String, partsName: String): Int?

    /**
     * 查询所有测温点
     * @return [List<Parts>]
     */
    @Query("SELECT * FROM temperature_measuring_point")
    suspend fun selectAllParts(): List<Parts>
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
    suspend fun insert(vararg data: Data)
    suspend fun deleteOldData() = deleteOldData(deleteTime())

    @Query("SELECT * FROM temperature_measuring_point_data")
    suspend fun select(): List<Data>

    @Query("DELETE FROM temperature_measuring_point_data WHERE time < :time")
    suspend fun deleteOldData(time: Long)
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
    suspend fun insert(vararg event: Event)
    suspend fun deleteOldEvent() = deleteOldEvent(deleteTime())

    @Query("DELETE FROM temperature_measuring_point_event WHERE time < :time")
    suspend fun deleteOldEvent(time: Long)
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
        "SELECT p.parts_id,p.device_name,p.parts_name,p.serial_number,p.sensor_type,d.temperature,d.voltage_rh,d.rssi,d.event_level,MAX(d.time) as time" +
                " FROM temperature_measuring_point AS p JOIN temperature_measuring_point_data AS d " +
                "ON p.parts_id=d.parts_id " +
                "GROUP BY d.parts_id"
    )
    suspend fun dataShow(): List<DataShow>

}

private fun deleteTime() = System.currentTimeMillis() - 86_400_000