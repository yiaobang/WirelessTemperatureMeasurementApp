package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.y.wirelesstemperaturemeasurement.room.entity.ShowData


@Dao
interface JoinTableDao {
    @Query(
        "SELECT " +
                "region.id," +
                "region.device_name," +
                "region.region_name," +
                "sensor.sensor_type," +
                "sensor_data.temperature," +
                "sensor_data.voltage_rh," +
                "sensor_data.rssi," +
                "sensor_data.time," +
                "sensor_data.event_level " +
                "FROM region " +
                "JOIN sensor ON region.sensor_id = sensor.id " +
                "JOIN sensor_data ON region.sensor_id = sensor_data.sensor_id " +
                "WHERE (sensor_data.sensor_id,sensor_data.time) IN (SELECT " +
                " sensor_id,MAX(time) FROM  sensor_data GROUP BY sensor_data.sensor_id)"
    )
            /**
             * 查询每个传感器的最新数据
             *
             * @return
             */
    fun selectEverySensorNewData(): List<ShowData>
}