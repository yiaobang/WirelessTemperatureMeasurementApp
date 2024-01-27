package com.y.wirelesstemperaturemeasurement.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.y.wirelesstemperaturemeasurement.data.parse.RH
import com.y.wirelesstemperaturemeasurement.data.parse.TEMP
import com.y.wirelesstemperaturemeasurement.data.parse.VOLTAGE

/**
 * @author Y
 * @date 2024/01/23
 * @constructor 创建[Parts] 测温点映射表
 * @param [id] 主键
 * @param [deviceName] 设备名称
 * @param [partsName]  设备部位名称
 * @param [serialNumber] 传感器序列号
 * @param [type] 传感器类型
 */
@Entity(
    tableName = "temperature_measuring_point",
    indices = [
        Index(value = ["parts_id"]),
        Index(value = ["device_name"]),
        Index(value = ["device_name", "parts_name"], unique = true),
        Index(value = ["serial_number"], unique = true)]
)
data class Parts(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "parts_id") var id: Int,
    @ColumnInfo(name = "device_name") var deviceName: String,
    @ColumnInfo(name = "parts_name") var partsName: String,
    @ColumnInfo(name = "serial_number") var serialNumber: Long,
    @ColumnInfo(name = "sensor_type") var type: Byte
)

/**
 * @author Y
 * @date 2024/01/23
 * @constructor 创建[Data] 测温点数据映射表
 * @param [id] 主键
 * @param [partsId] 测温点ID
 * @param [temperature] 温度
 * @param [voltageRH] 供电电压/湿度
 * @param [rssi] 信号强度
 * @param [eventLevel] 事件
 * @param [time] 时间
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Parts::class,
        parentColumns = ["parts_id"],
        childColumns = ["parts_id"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "temperature_measuring_point_data",
    indices = [
        Index(value = ["id"]),
        Index(value = ["parts_id"]),
        Index(value = ["time"])
    ]
)
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "parts_id") val partsId: Int = 0,
    @ColumnInfo(name = "temperature") val temperature: Double = 0.0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Short = 0,
    @ColumnInfo(name = "rssi") val rssi: Byte = -70,
    @ColumnInfo(name = "event_level") val eventLevel: Byte = 0,
    @ColumnInfo(name = "time") val time: Long = System.currentTimeMillis()
)

/**
 * @author Y
 * @date 2024/01/23
 * @constructor 创建[Event] 测温点事件映射表
 * @param [id] 事件id
 * @param [partsId] 测温点id
 * @param [temperature]温度
 * @param [voltageRH]电压/湿度
 * @param [rssi] 信号强度
 * @param [eventLevel]事件类型
 * @param [time]时间
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Parts::class,
        parentColumns = ["parts_id"],
        childColumns = ["parts_id"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "temperature_measuring_point_event",
    indices = [
        Index(value = ["id"]),
        Index(value = ["parts_id"]),
        Index(value = ["time"])
    ]
)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "parts_id") val partsId: Int = 0,
    @ColumnInfo(name = "temperature") val temperature: Double = 0.0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Short = 0,
    @ColumnInfo(name = "rssi") val rssi: Byte = -70,
    @ColumnInfo(name = "event_level") val eventLevel: Byte = 0,
    @ColumnInfo(name = "time") val time: Long = System.currentTimeMillis()
)

/**
 * @author Y
 * @date 2024/01/24
 * @constructor 创建[DataShow] 联合查询的时候使用
 * @param [id] 主键
 * @param [deviceName] 设备名称
 * @param [partsName] 测温点名称
 * @param [serialNumber] 传感器序列号
 * @param [type] 传感器类型
 * @param [temperature] 温度
 * @param [voltageRH] 电压/湿度
 * @param [rssi] 信号强度
 * @param [eventLevel] 事件级别
 * @param [time] 时间
 */
data class DataShow(
    @ColumnInfo(name = "parts_id") val id: Int = 0,
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "parts_name") val partsName: String,
    @ColumnInfo(name = "serial_number") val serialNumber: Long,
    @ColumnInfo(name = "sensor_type") val type: Byte,
    @ColumnInfo(name = "temperature") val temperature: Double = 0.0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Short = 0,
    @ColumnInfo(name = "rssi") val rssi: Byte = -70,
    @ColumnInfo(name = "event_level") val eventLevel: Byte = 0,
    @ColumnInfo(name = "time") val time: Long = System.currentTimeMillis()
)

fun getTemperature(dataShow: DataShow?): String {
    return if (dataShow == null) {
        "--"
    } else {
        "${dataShow.temperature}$TEMP"
    }
}


fun getVoltageRH(dataShow: DataShow?): String {
    return if (dataShow == null) {
        "--"
    } else {
        when (dataShow.type) {
            1.toByte() -> "${dataShow.voltageRH / 100.0}$RH"
            else -> "${dataShow.voltageRH / 1000.0}$VOLTAGE"
        }
    }
}

fun List<Parts>.haveId(id: Int): Boolean {
    this.forEach { if (it.id == id) return true }
    return false
}

fun List<Parts>.haveSerialNumber(serialNumber: Long): Boolean {
    this.forEach { if (it.serialNumber == serialNumber) return true }
    return false
}
