package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.y.wirelesstemperaturemeasurement.room.Event

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[EventDao]
 * 查询事件
 */
@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)
}