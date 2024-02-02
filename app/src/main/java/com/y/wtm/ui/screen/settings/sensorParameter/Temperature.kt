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
import com.y.wtm.config.TSensorAlarmMaxTemp
import com.y.wtm.config.TSensorAlarmMinTemp
import com.y.wtm.config.TSensorMaxTemp
import com.y.wtm.config.TSensorMinTemp
import com.y.wtm.config.show
import com.y.wtm.config.slave
import com.y.wtm.serialport.TEMP
import com.y.wtm.room.isNumberParameter
import com.y.wtm.ui.components.TopBar
import com.y.wtm.ui.components.showToast

@Composable
fun Temperature() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("设备温度传感器") }) { paddingValues ->
        TemperatureContent(paddingValues)
    }
}

@Composable
private fun TemperatureContent(paddingValues: PaddingValues) {
    var minTemp by remember { mutableStateOf(TSensorMinTemp.show()) }
    var maxTemp by remember { mutableStateOf(TSensorMaxTemp.show()) }
    var alarmMinTemp by remember { mutableStateOf(TSensorAlarmMinTemp.show()) }
    var alarmMaxTemp by remember { mutableStateOf(TSensorAlarmMaxTemp.show()) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        Button(onClick = {
            try {
                val minT = minTemp.toInt()
                val maxT = maxTemp.toInt()
                val alarmMinT = alarmMinTemp.toInt()
                val alarmMaxT = alarmMaxTemp.toInt()
                if (minT < maxT && alarmMinT < alarmMaxT && alarmMinT < minT && maxT < alarmMaxT) {
                    TSensorMinTemp = minT
                    TSensorMaxTemp = maxT
                    TSensorAlarmMinTemp = alarmMinT
                    TSensorAlarmMaxTemp = alarmMaxT
                    Config.writeConfig("sensor:T-minT", minT.slave())
                    Config.writeConfig("sensor:T-maxT", maxT.slave())
                    Config.writeConfig("sensor:T-alarmMinT", alarmMinT.slave())
                    Config.writeConfig("sensor:T-alarmMaxT", alarmMaxT.slave())
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

