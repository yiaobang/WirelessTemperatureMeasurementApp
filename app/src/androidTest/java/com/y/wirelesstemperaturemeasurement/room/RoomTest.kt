package com.y.wirelesstemperaturemeasurement.room

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.y.wirelesstemperaturemeasurement.room.DataBase.Companion.initDataBase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    lateinit var database: DataBase
    lateinit var dataDao: DataDao
    lateinit var jointDao: JointDao

    @Before
    fun createDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = initDataBase(appContext)
        dataDao = database.dataDao()
        jointDao = database.jointDao()
    }

    @After
    fun close() {
        database.close()
    }

//    @Test
//    fun test() {
//        dataDao.select().forEach { Log.d(TAG, "test: $it") }
//        println("========================================")
//        val dataShow = jointDao.dataShow()
//        dataShow.forEach { Log.d(TAG, "test: $it") }
//        println("====================================")
//        val associateBy = dataShow.associateBy { it.serialNumber }
//        associateBy.forEach{(index,data)-> Log.d(TAG, "$index = $data")}
//    }
}