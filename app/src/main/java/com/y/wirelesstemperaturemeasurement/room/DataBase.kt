package com.y.wirelesstemperaturemeasurement.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


lateinit var DATA_BASE: DataBase
lateinit var PARTS_DAO: PartsDao
lateinit var DATE_DAO: DataDao
lateinit var EVENT_DAO: EventDao
lateinit var JOINT_DAO: JointDao

@Database(
    entities = [Parts::class, Data::class, Event::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    companion object {
        private var dataBase: DataBase? = null
        private const val DATA_BESE_NAME = "wireless_temperature_measurement_database"
        fun initDataBase(application: Context): DataBase {
            if (dataBase == null) {
                synchronized(DataBase::class.java) {
                    if (dataBase == null) {
                        dataBase = Room.databaseBuilder(
                            application,
                            DataBase::class.java,
                            DATA_BESE_NAME
                        )
                            //允许在主线程使用数据库
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return dataBase as DataBase
        }
    }

    abstract fun partsDao(): PartsDao
    abstract fun dataDao(): DataDao
    abstract fun eventDao(): EventDao
    abstract fun jointDao(): JointDao
}