package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.R
import com.y.wirelesstemperaturemeasurement.room.Parts
import com.y.wirelesstemperaturemeasurement.room.temp
import com.y.wirelesstemperaturemeasurement.room.voltageRH
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.RoomViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.PARTS
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel.SensorDataMap

@Composable
fun Home() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottom() }) { paddingValues -> HomeContent(paddingValues) }
}

@Composable
private fun HomeContentTitle() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "id"
        )
        Text(
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.4f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "设备名称"
        )
        Text(
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.5f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "测温点名称"
        )
        Text(
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "温度"
        )
        Text(
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.25f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "电压/湿度"
        )
    }
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        HomeContentTitle()
        ShowData()
    }
}

@Composable
private fun ShowData() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(PARTS) {
            Data(it)
        }
    }
}

@Composable
private fun Data(parts: MutableList<Parts>) {
    Row(
        Modifier
            .fillMaxWidth()
            .height((24 * parts.size).dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .weight(0.2f)
                .fillMaxHeight()
        ) {
            parts.forEach {
                Text(
                    fontSize = 18.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(1.dp, Color.Black)
                        .wrapContentSize(Alignment.Center),
                    text = it.id.toString()
                )
            }
        }
        Text(
            fontSize = 18.sp,
            maxLines = 1,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = parts[0].deviceName
        )
        Column(
            Modifier
                .weight(1.05f)
                .fillMaxHeight()
        ) {
            parts.forEach { currentParts ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        fontSize = 18.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.5f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = currentParts.partsName
                    )
                    Text(
                        fontSize = 18.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.3f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = SensorDataMap[currentParts.serialNumber].temp()
                    )
                    Text(
                        fontSize = 18.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.25f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = SensorDataMap[currentParts.serialNumber].voltageRH()
                    )
                }
            }
        }
    }
}


@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(ThemeColor),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(10.dp),
            onClick = { NavHostViewModel.navigate("Menu") }) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Menu,
                contentDescription = "菜单"
            )
        }
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
private fun HomeBottom() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(ThemeColor),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { StateViewModel.updateAudible() }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(if (StateViewModel.audible) R.drawable.mute_normal else R.drawable.mute_press),
                contentDescription = ""
            )
        }
        SensorMapping()
        WarnMsg()
        ErrorMsg()
        EventMsg()
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
private fun SensorMapping() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { NavHostViewModel.navigate("Menu/SensorMap") }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.bbar_point),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "系统共接入${StateViewModel.partsNumber}个测温点"
        )
    }
}

@Composable
private fun WarnMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.bbar_warn),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "${StateViewModel.warn}条预警信息"
        )
    }
}

@Composable
private fun ErrorMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Row {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.bbar_new),
                    contentDescription = ""
                )
            }

        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "${StateViewModel.error}条报警消息"
        )
    }
}

@Composable
private fun EventMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { RoomViewModel.updateData() }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "最近更新:${StateViewModel.dataTime}"
        )
    }
}