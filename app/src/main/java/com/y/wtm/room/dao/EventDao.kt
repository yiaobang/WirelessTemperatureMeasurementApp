package com.y.wtm.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.y.wtm.room.Event

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