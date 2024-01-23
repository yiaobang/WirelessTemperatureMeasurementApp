package com.y.wirelesstemperaturemeasurement

import com.y.wirelesstemperaturemeasurement.data.parse.timeSum

fun timeSumInit(){
    timeSum[1812400098] = 0
    timeSum[2007271002] = 0
    timeSum[2007271006] = 0
    timeSum[2007271009] = 0
    timeSum[2007271010] = 0

    timeSum[1911036766] = 0
    timeSum[1911036771] = 0
    timeSum[1911036777] = 0
    timeSum[1911036782] = 0
    timeSum[1911036826] = 0
    timeSum[2005075887] = 0
    timeSum[2005075898] = 0
}
//fun sensors() = arrayOf(
//    Sensor(serialNumber = 1812400098, type = 1),
//    Sensor(serialNumber = 2007271002, type = 1),
//    Sensor(serialNumber = 2007271006, type = 1),
//    Sensor(serialNumber = 2007271009, type = 1),
//    Sensor(serialNumber = 2007271010, type = 1),
//
//    Sensor(serialNumber = 1911036766, type = 0),
//    Sensor(serialNumber = 1911036771, type = 0),
//    Sensor(serialNumber = 1911036777, type = 0),
//    Sensor(serialNumber = 1911036782, type = 0),
//    Sensor(serialNumber = 1911036826, type = 0),
//    Sensor(serialNumber = 2005075887, type = 0),
//    Sensor(serialNumber = 2005075898, type = 0)
//)
//fun regions()= arrayOf(
//    Region(deviceName = "移动测温点", regionName = "1", sensorId = 1),
//    Region(deviceName = "移动测温点", regionName = "2", sensorId = 2),
//    Region(deviceName = "移动测温点", regionName = "3", sensorId = 3),
//    Region(deviceName = "移动测温点", regionName = "4", sensorId = 4),
//    Region(deviceName = "移动测温点", regionName = "5", sensorId = 5),
//
//    Region(deviceName = "空调", regionName = "1", sensorId = 6),
//    Region(deviceName = "空调", regionName = "2", sensorId = 7),
//    Region(deviceName = "空调", regionName = "3", sensorId = 8),
//    Region(deviceName = "空调", regionName = "4", sensorId = 9),
//    Region(deviceName = "空调", regionName = "5", sensorId = 10),
//    Region(deviceName = "空调", regionName = "6", sensorId = 11),
//    Region(deviceName = "空调", regionName = "7", sensorId = 12)
//)