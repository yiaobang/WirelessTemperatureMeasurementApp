package com.y.wirelesstemperaturemeasurement.ui.screen.settings.sensorParameter

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.config.Config.readConfig
import com.y.wirelesstemperaturemeasurement.data.parse.TEMP
import com.y.wirelesstemperaturemeasurement.room.isNumberParameter
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar

@Composable
fun Temperature() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("设备温度传感器") }) { paddingValues ->
        TemperatureContent(paddingValues)
    }
}

@Composable
private fun TemperatureContent(paddingValues: PaddingValues) {
    var minTemp by remember { mutableStateOf(readConfig("温度-min温度", "20")) }
    var maxTemp by remember { mutableStateOf(readConfig("温度-max温度", "80")) }
    var alarmMinTemp by remember { mutableStateOf(readConfig("温度-min预警温度", "0")) }
    var alarmMaxTemp by remember { mutableStateOf(readConfig("温度-max预警温度", "100")) }
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }
        Button(onClick = { }) {
            Text(text = "确认")
        }
    }
}

