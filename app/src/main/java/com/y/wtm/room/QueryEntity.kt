package com.y.wtm.room

import androidx.room.ColumnInfo

/**
 * 实时数据
 * @author Y
 * @date 2024/01/29
 * @constructor 创建[NowData]
 * @param [serialNumber] 序列号
 * @param [type] 类型
 * @param [temperature] 温度
 * @param [voltageRH] 电压/湿度
 */
data class NowData(
    @ColumnInfo(name = "serial_number") val serialNumber: Long,
    @ColumnInfo(name = "sensor_type") var type: Int,
    @ColumnInfo(name = "temperature") val temperature: Int = 0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Int = 0
)
/**
 * 历史数据
 * @author Y
 * @date 2024/01/29
 * @constructor 创建[HistoryData]
 * @param [id] 测温点ID
 * @param [deviceName]设备名称
 * @param [partsName]测温点名称
 * @param [serialNumber]序列号
 * @param [type] 类型
 * @param [temperature] 温度
 * @param [voltageRH] 电压/湿度
 * @param [time] 时间
 */
data class HistoryData(
    @ColumnInfo(name = "parts_id") var id: Long,
    @ColumnInfo(name = "device_name") var deviceName: String,
    @ColumnInfo(name = "parts_name") var partsName: String,
    @ColumnInfo(name = "serial_number") var serialNumber: Long,
    @ColumnInfo(name = "sensor_type") var type: Int,
    @ColumnInfo(name = "temperature") val temperature: Int = 0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Int = 0,
    @ColumnInfo(name = "time") val time: Long
)
/**
 * 查看事件
 * @author Y
 * @date 2024/01/29
 * @constructor 创建[ShowEvent]
 * @param [id] 测温点ID
 * @param [deviceName]设备名称
 * @param [partsName]测温点名称
 * @param [serialNumber]序列号
 * @param [type]类型
 * @param [temperature] 温度
 * @param [voltageRH] 湿度
 * @param [eventLevel] 事件级别
 * @param [eventMsg] 事件信息
 * @param [time] 发生时间
 */
data class ShowEvent(
    @ColumnInfo(name = "parts_id") var id: Long,
    @ColumnInfo(name = "device_name") var deviceName: String,
    @ColumnInfo(name = "parts_name") var partsName: String,
    @ColumnInfo(name = "serial_number") var serialNumber: Long,
    @ColumnInfo(name = "sensor_type") var type: Int,
    @ColumnInfo(name = "temperature") val temperature: Int = 0,
    @ColumnInfo(name = "voltage_rh") val voltageRH: Int = 0,
    @ColumnInfo(name = "event_level") val eventLevel: Int = 0,
    @ColumnInfo(name = "event_msg") val eventMsg: String,
    @ColumnInfo(name = "time") val time: Long
)
