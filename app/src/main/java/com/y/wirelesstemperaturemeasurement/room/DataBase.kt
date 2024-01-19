package com.y.wirelesstemperaturemeasurement.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.y.wirelesstemperaturemeasurement.room.dao.JoinTableDao
import com.y.wirelesstemperaturemeasurement.room.dao.RegionDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorDataDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorEventDao
import com.y.wirelesstemperaturemeasurement.room.entity.Region
import com.y.wirelesstemperaturemeasurement.room.entity.Sensor
import com.y.wirelesstemperaturemeasurement.room.entity.SensorData
import com.y.wirelesstemperaturemeasurement.room.entity.SensorEvent

private const val DATA_BESE_NAME = "wireless_temperature_measurement_database"
@Database(
    entities = [Region::class, Sensor::class, SensorEvent::class, SensorData::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    companion object {
        private var dataBase: DataBase? = null
        fun getDataBase(application: Context): DataBase {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(
                    application,
                    DataBase::class.java,
                    DATA_BESE_NAME
                )
//                    //允许在主线程使用数据库   实际环境不要用
//                    .allowMainThreadQueries()
                    .build()
            }
            return dataBase as DataBase
        }
    }
    abstract fun regionDao(): RegionDao
    abstract fun sensorDao(): SensorDao
    abstract fun sensorEventDao(): SensorEventDao
    abstract fun sensorDataDao(): SensorDataDao
    abstract fun joinTableDao():JoinTableDao
}