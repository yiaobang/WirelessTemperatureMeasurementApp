package com.y.wirelesstemperaturemeasurement

import com.y.wirelesstemperaturemeasurement.room.entity.Region
import com.y.wirelesstemperaturemeasurement.room.entity.Sensor

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
fun regions()= arrayOf(
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