package com.y.wirelesstemperaturemeasurement.room

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.y.wirelesstemperaturemeasurement.dataBase
import com.y.wirelesstemperaturemeasurement.room.dao.JoinTableDao
import com.y.wirelesstemperaturemeasurement.room.dao.RegionDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorDataDao
import com.y.wirelesstemperaturemeasurement.room.dao.SensorEventDao
import com.y.wirelesstemperaturemeasurement.room.entity.Region
import com.y.wirelesstemperaturemeasurement.room.entity.Sensor
import com.y.wirelesstemperaturemeasurement.room.entity.SensorData
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Room {
    private lateinit var databaseTest: DataBase
    private lateinit var regionDao: RegionDao
    private lateinit var sensorDao: SensorDao
    private lateinit var sensorEventDao: SensorEventDao
    private lateinit var sensorDataDao: SensorDataDao
    private lateinit var joinTableDao: JoinTableDao

    @Before
    fun createDataBase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        databaseTest = DataBase.getDataBase(appContext.applicationContext)
        regionDao = databaseTest.regionDao()
        sensorDao = databaseTest.sensorDao()
        sensorEventDao = databaseTest.sensorEventDao()
        sensorDataDao = databaseTest.sensorDataDao()
        joinTableDao = databaseTest.joinTableDao()
    }

    @After
    fun close() {
        databaseTest.close()
    }

    @Test
    fun updateSensor() {
        addSensor()
        sensorDao.updateSensor(Sensor(id = 1, serialNumber = 1812400000, type = 0))
        sensorDao.selectSensors().forEach { Log.i("传感器", "add: $it") }
    }


    @Test
    fun serialNumberTest() {
        addSensor()
        selectSensorBySerialNumber(12345)
        selectSensorBySerialNumber(5325326236)
        selectSensorBySerialNumber(123452553255)
        selectSensorBySerialNumber(125325345)
        selectSensorBySerialNumber(12525345)
        selectSensorBySerialNumber(12253535345)
        selectSensorBySerialNumber(2007271006)
        selectSensorBySerialNumber(2007271009)
        selectSensorBySerialNumber(2007271010)
    }

    fun selectSensorBySerialNumber(serialNumber: Long) {
        val selectId = sensorDao.selectId(serialNumber)
        if (selectId == null) Log.d("selectSensorBySerialNumber", "没有 $serialNumber") else Log.d(
            "selectSensorBySerialNumber",
            "id = $selectId serialNumber = $serialNumber "
        )
    }

    @Test
    fun selectEverySensorNewData() {
        addSensorData()
        joinTableDao.selectEverySensorNewData()
            .forEach { Log.i("查询每一个传感器最新的数据", "datanew: $it") }
    }

    @Test
    fun addSensor() {
        sensorDao.addSensor(*sensors())
        sensorDao.selectSensors().forEach { Log.i("传感器", "add: $it") }
    }

    @Test
    fun addRegion() {
        regionDao.addRegion(*regions())
        regionDao.selectRegions().forEach { Log.i("测温点", "addRegion: $it") }
    }

    @Test
    fun addSensorData() {
        addSensor()
        addRegion()
        for (i in 1..50) {
            sensorDao.selectSensors().forEach {
                sensorDataDao.addSensorData(SensorData(sensorId = it.id))
            }
        }
        sensorDataDao.selectSensorDatas().forEach { Log.d("传感器数据", "addData: $it") }
    }
}

fun sensors() = arrayOf(
    Sensor(serialNumber = 1812400098, type = 0),
    Sensor(serialNumber = 2007271002, type = 0),
    Sensor(serialNumber = 2007271006, type = 0),
    Sensor(serialNumber = 2007271009, type = 0),
    Sensor(serialNumber = 2007271010, type = 0),

    Sensor(serialNumber = 1911036766, type = 1),
    Sensor(serialNumber = 1911036771, type = 1),
    Sensor(serialNumber = 1911036777, type = 1),
    Sensor(serialNumber = 1911036782, type = 1),
    Sensor(serialNumber = 1911036826, type = 1),
    Sensor(serialNumber = 2005075887, type = 1),
    Sensor(serialNumber = 2005075898, type = 1)
)

fun regions() = arrayOf(
    Region(deviceName = "移动测温点", regionName = "1", sensorId = 1),
    Region(deviceName = "移动测温点", regionName = "2", sensorId = 2),
    Region(deviceName = "移动测温点", regionName = "3", sensorId = 3),
    Region(deviceName = "移动测温点", regionName = "4", sensorId = 4),
    Region(deviceName = "移动测温点", regionName = "5", sensorId = 5),

    Region(deviceName = "空调", regionName = "1", sensorId = 6),
    Region(deviceName = "空调", regionName = "2", sensorId = 7),
    Region(deviceName = "空调", regionName = "3", sensorId = 8),
    Region(deviceName = "空调", regionName = "4", sensorId = 9),
    Region(deviceName = "空调", regionName = "5", sensorId = 10),
    Region(deviceName = "空调", regionName = "6", sensorId = 11),
    Region(deviceName = "空调", regionName = "7", sensorId = 12)
)
