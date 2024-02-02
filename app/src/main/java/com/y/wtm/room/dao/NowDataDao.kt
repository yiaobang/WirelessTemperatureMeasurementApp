package com.y.wtm.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.y.wtm.room.NowData

@Dao
interface NowDataDao {

    /**
     * 查询每个测温点的最新数据
     * @return [List<NowData>]
     */
    @Query(
        "SELECT serial_number,sensor_type,temperature,voltage_rh " +
                "FROM temperature_measuring_point AS P JOIN temperature_measuring_point_data  AS D " +
                "ON P.parts_id = D.parts_id AND " +
                "D.time = (SELECT MAX(time) FROM temperature_measuring_point_data WHERE parts_id = P.parts_id) " +
                "GROUP BY D.parts_id "
    )
    suspend fun currentData(): List<NowData>
}