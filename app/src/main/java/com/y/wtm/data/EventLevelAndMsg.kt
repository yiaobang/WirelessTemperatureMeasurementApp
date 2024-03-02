package com.y.wtm.data

import com.y.wtm.config.THSensorAlarmMaxRH
import com.y.wtm.config.THSensorAlarmMinRH
import com.y.wtm.config.THSensorMaxRH
import com.y.wtm.config.THSensorMinRH
import com.y.wtm.config.TSensorAlarmMaxTemp
import com.y.wtm.config.TSensorAlarmMinTemp
import com.y.wtm.config.TSensorMaxTemp
import com.y.wtm.config.TSensorMinTemp
import com.y.wtm.room.Data
import com.y.wtm.room.eventMsg

data class EventLevelAndMsg(
    val eventLevel: Int,
    val eventMsg: String
)
/**
 * 数据级别检查
 * @param [data] 数据
 */
fun eventLevelCheck(type: Int, data: Data): EventLevelAndMsg? =
    when (type) {
        1 -> tempSensor(data)
        2 -> tempHSensor(data)
        else -> null
    }

/**
 * 温度传感器事件检查
 *
 * @param data
 */
fun tempSensor(data: Data): EventLevelAndMsg? {
    val temp = TSensorMinTemp..TSensorMaxTemp
    val alarmTemp = TSensorAlarmMinTemp..TSensorAlarmMaxTemp
    //判断事件级别
    val event = when (data.temperature) {
        in temp -> 0
        in alarmTemp -> 10
        else -> 20
    }
    return if (event > 0) {
        EventLevelAndMsg(event, event.eventMsg(data.temperature))
    } else {
        null
    }
}
/**
 * 温湿度传感器事件检查
 *
 * @param data
 */
fun tempHSensor(data: Data): EventLevelAndMsg? {
    val temp = TSensorMinTemp..TSensorMaxTemp
    val alarmTemp = TSensorAlarmMinTemp..TSensorAlarmMaxTemp
    val rh = THSensorMinRH..THSensorMaxRH
    val alarmRh = THSensorAlarmMinRH..THSensorAlarmMaxRH
    var event = 0
    when (data.temperature) {
        in temp -> {
            event += when (data.voltageRH) {
                in rh -> 0
                in alarmRh -> 1
                else -> 2
            }
        }

        in alarmTemp -> {
            event += 10
            event += when (data.voltageRH) {
                in rh -> 0
                in alarmRh -> 1
                else -> 2
            }
        }

        else -> {
            event += 20
            event += when (data.voltageRH) {
                in rh -> 0
                in alarmRh -> 1
                else -> 2
            }
        }
    }
    return if (event > 0) {
        EventLevelAndMsg(event, event.eventMsg(data.temperature,data.voltageRH))
    } else {
        null
    }
}