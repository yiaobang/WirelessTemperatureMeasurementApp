package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.y.wirelesstemperaturemeasurement.room.Parts

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[PartsDao]
 * 查询测温点
 */
@Dao
interface PartsDao {
    @Insert
    suspend fun insert(parts: Parts):Long
    /**
     * 查询所有测温点
     * @return [List<Parts>]
     */
    @Query("SELECT * FROM temperature_measuring_point")
    suspend fun selectAllParts(): List<Parts>
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
    suspend fun update(id: Long, newId: Long, deviceName: String, partsName: String, serialNumber: Long, type: Byte)

    /**
     * 根据ID查询测温点是否存在
     * @param [id]
     * @return [Parts?]
     */
    @Query("SELECT * FROM temperature_measuring_point WHERE parts_id = :id")
    suspend fun selectId(id: Long): Parts?

    /**
     * 根据ID删除测温点
     * @param [id]
     */
    @Query("DELETE FROM temperature_measuring_point WHERE parts_id = :id")
    suspend fun deleteId(id:Int)

    /**
     * 根据 ID 或者设备名称+测温点名称  或者传感器序列号 查询 是否有符合条件的测温点
     * @param [id]
     * @param [deviceName]
     * @param [partsName]
     * @param [serialNumber]
     * @return [List<Parts>?]
     */
    @Query("SELECT * FROM temperature_measuring_point WHERE parts_id=:id OR (device_name=:deviceName AND parts_name=:partsName) OR serial_number=:serialNumber")
    suspend fun selectParts(id: Long, deviceName: String, partsName: String, serialNumber: Long): List<Parts>?

    /**
     * 根据传感器序列号查询是否存在测温点
     * @param [serialNumber] 传感器序列号
     * @return [Int?]
     */
    @Query("SELECT parts_id FROM temperature_measuring_point WHERE serial_number = :serialNumber")
    suspend fun selectSerialNumber(serialNumber: Long): Int?

}