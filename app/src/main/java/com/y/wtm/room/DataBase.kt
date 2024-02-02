package com.y.wtm.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.y.wtm.TAG
import com.y.wtm.room.dao.DataDao
import com.y.wtm.room.dao.EventDao
import com.y.wtm.room.dao.HistoryDataDao
import com.y.wtm.room.dao.NowDataDao
import com.y.wtm.room.dao.PartsDao
import com.y.wtm.room.dao.ShowEventDao


@Database(
    entities = [Parts::class, Data::class, Event::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    companion object {
        private var dataBase: DataBase? = null
        private const val DATA_BASE_NAME = "wireless_temperature_measurement_database"
        fun initDataBase(application: Context): DataBase {
            if (dataBase == null) {
                synchronized(DataBase::class.java) {
                    if (dataBase == null) {
                        dataBase = Room.databaseBuilder(
                            application,
                            DataBase::class.java,
                            DATA_BASE_NAME
                        ).build()
                    }
                }
            }
            Log.d(TAG, "initDataBase: 数据库初始化完成")
            return dataBase as DataBase
        }
    }

    abstract fun partsDao(): PartsDao
    abstract fun dataDao(): DataDao
    abstract fun eventDao(): EventDao


    abstract fun historyDao(): HistoryDataDao
    abstract fun nowDataDao(): NowDataDao
    abstract fun showEventDao(): ShowEventDao

}