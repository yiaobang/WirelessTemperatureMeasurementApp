package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.y.wirelesstemperaturemeasurement.MONTH
import com.y.wirelesstemperaturemeasurement.room.Data

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[DataDao]
 * 查询数据
 */
@Dao
interface DataDao {
    @Insert
    suspend fun insert(data: Data): Long
    suspend fun deleteOldData() = deleteOldData(deleteTime())
    @Query("DELETE FROM temperature_measuring_point_data WHERE time < :time")
    suspend fun deleteOldData(time: Long)
    private fun deleteTime() = System.currentTimeMillis() - MONTH
}