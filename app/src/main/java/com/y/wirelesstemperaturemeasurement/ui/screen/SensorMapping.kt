package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.room.entity.Device
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.utils.sensorType
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@Composable
fun SensorMap() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("设备映射") }) { paddingValues ->
        SensorMapContent(paddingValues)

    }
}

@Composable
private fun Title() {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center), text = "传感器序号"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.4f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "设备名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.6f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "测温点名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.4f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "传感器序列号"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "传感器类型"
        )
    }
}

@Composable
fun SensorMapContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Title()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(StateViewModel.Devices) { device ->
                Device(device)
            }
        }
    }
}

@Composable
private fun Device(device: Device) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.2f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center), text = device.id.toString()
        )
        Text(
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.4f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = device.deviceName
        )
        Text(
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.6f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = device.regionName
        )
        Text(
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.4f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = device.serialNumber.toString()
        )
        Text(
            fontSize = 18.sp,
            modifier = Modifier
                .weight(0.2f)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = device.type.sensorType()
        )
    }
}

