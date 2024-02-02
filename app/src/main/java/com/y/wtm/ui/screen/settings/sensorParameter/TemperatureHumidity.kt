package com.y.wtm.ui.screen.settings.sensorParameter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wtm.config.Config
import com.y.wtm.config.THSensorAlarmMaxRH
import com.y.wtm.config.THSensorAlarmMaxTemp
import com.y.wtm.config.THSensorAlarmMinRH
import com.y.wtm.config.THSensorAlarmMinTemp
import com.y.wtm.config.THSensorMaxRH
import com.y.wtm.config.THSensorMaxTemp
import com.y.wtm.config.THSensorMinRH
import com.y.wtm.config.THSensorMinTemp
import com.y.wtm.config.show
import com.y.wtm.config.slave
import com.y.wtm.serialport.RH
import com.y.wtm.serialport.TEMP
import com.y.wtm.room.isNumberParameter
import com.y.wtm.ui.components.TopBar
import com.y.wtm.ui.components.showToast

@Composable
fun TemperatureHumidity() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("环境温湿度传感器") }) { paddingValues ->
        TemperatureHumidityContent(paddingValues)
    }
}

@Composable
private fun TemperatureHumidityContent(paddingValues: PaddingValues) {

    var minTemp by remember { mutableStateOf(THSensorMinTemp.show()) }
    var maxTemp by remember { mutableStateOf(THSensorMaxTemp.show()) }

    var alarmMinTemp by remember { mutableStateOf(THSensorAlarmMinTemp.show()) }
    var alarmMaxTemp by remember { mutableStateOf(THSensorAlarmMaxTemp.show()) }

    var minHumidity by remember { mutableStateOf(THSensorMinRH.show()) }
    var maxHumidity by remember { mutableStateOf(THSensorMaxRH.show()) }

    var alarmMinHumidity by remember { mutableStateOf(THSensorAlarmMinRH.show()) }
    var alarmMaxHumidity by remember { mutableStateOf(THSensorAlarmMaxRH.show()) }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "正常温度范围")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最低温度$TEMP", fontSize = 10.sp) },
                value = minTemp,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        minTemp = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Text(text = "~")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最高温度$TEMP", fontSize = 10.sp) },
                value = maxTemp,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        maxTemp = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "预警温度范围")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最低温度$TEMP", fontSize = 10.sp) },
                value = alarmMinTemp,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        alarmMinTemp = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Text(text = "~")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最高温度$TEMP", fontSize = 10.sp) },
                value = alarmMaxTemp,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        alarmMaxTemp = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "正常湿度范围")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最低湿度$RH", fontSize = 10.sp) },
                value = minHumidity,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        minHumidity = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Text(text = "~")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最高湿度$RH", fontSize = 10.sp) },
                value = maxHumidity,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        maxHumidity = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "预警湿度范围")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最低湿度$RH", fontSize = 10.sp) },
                value = alarmMinHumidity,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        alarmMinHumidity = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Text(text = "~")
            TextField(
                modifier = Modifier.width(200.dp),
                label = { Text(text = "最高湿度$RH", fontSize = 10.sp) },
                value = alarmMaxHumidity,
                onValueChange = {
                    if (it.isNumberParameter()) {
                        alarmMaxHumidity = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Button(onClick = {
            try {
                val minT = minTemp.toInt()
                val maxT = maxTemp.toInt()
                val alarmMinT = alarmMinTemp.toInt()
                val alarmMaxT = alarmMaxTemp.toInt()
                val minH = minHumidity.toInt()
                val maxH = maxHumidity.toInt()
                val alarmMinH = alarmMinHumidity.toInt()
                val alarmMaxH = alarmMaxHumidity.toInt()
                if (minT < maxT && alarmMinT < alarmMaxT && alarmMinT < minT && maxT < alarmMaxT &&
                    minH < maxH && alarmMinH < alarmMaxH && alarmMinH < minH && maxH < alarmMaxH
                ) {
                    THSensorMinTemp = minT
                    THSensorMaxTemp = maxT
                    THSensorAlarmMinTemp = alarmMinT
                    THSensorAlarmMaxTemp = alarmMaxT
                    THSensorMinRH = minH
                    THSensorMaxRH = maxH
                    THSensorAlarmMinRH = alarmMinH
                    THSensorAlarmMaxRH = alarmMaxH
                    Config.writeConfig("sensor:TH-minT", minT.slave())
                    Config.writeConfig("sensor:TH-maxT", maxT.slave())
                    Config.writeConfig("sensor:TH-alarmMinT", alarmMinT.slave())
                    Config.writeConfig("sensor:TH-alarmMaxT", alarmMaxT.slave())
                    Config.writeConfig("sensor:TH-minT", minH.slave())
                    Config.writeConfig("sensor:TH-maxT", maxH.slave())
                    Config.writeConfig("sensor:TH-alarmMinT", alarmMinH.slave())
                    Config.writeConfig("sensor:TH-alarmMaxT", alarmMaxH.slave())
                    showToast(context, "设置成功")
                } else {
                    showToast(context, "参数有误")
                }
            } catch (e: Exception) {
                showToast(context, "参数有误")
            }
        }) {
            Text(text = "确认")
        }
    }
}
