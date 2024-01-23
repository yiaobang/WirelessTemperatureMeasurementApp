package com.y.wirelesstemperaturemeasurement.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BESE_NAME = "wireless_temperature_measurement_database"
@Database(
    entities = [Parts::class, Data::class, Event::class],
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
                    .allowMainThreadQueries()
                    .build()
            }
            return dataBase as DataBase
        }
    }
    abstract fun partsDao():PartsDao
    abstract fun dataDao():DataDao
    abstract fun eventDao():EventDao
}