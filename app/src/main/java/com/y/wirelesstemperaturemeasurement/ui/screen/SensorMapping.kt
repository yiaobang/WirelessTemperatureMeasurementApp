package com.y.wirelesstemperaturemeasurement.ui.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.room.Parts
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping.AddParts
import com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping.DeleteParts
import com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping.UpdateParts
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.utils.sensorType
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@Composable
fun SensorMap() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("设备映射") },
        floatingActionButton = { FloatingActionButton() },
        floatingActionButtonPosition = FabPosition.End,
//        bottomBar = { Bottom() }
    ) { paddingValues ->
        SensorMapContent(paddingValues)
    }
}

@Composable
fun FloatingActionButton() {
    var expand by remember { mutableStateOf(false) }
    //添加测温点的窗口
    var add by remember { mutableStateOf(false) }
    AddParts(add) { add = it }
    //更新测温点的窗口
    var update by remember { mutableStateOf(false) }
    UpdateParts(update) { update = it }
    //删除测温点的窗口
    var delete by remember { mutableStateOf(false) }
    DeleteParts(delete) { delete = it }
    Row(
        modifier = Modifier
            .height(50.dp)
    ) {
        if (expand) {
            FloatingActionButtonItem(Icons.Default.Add, "Add") {
                add = true
                expand = false
            }
            ShuXian()
            FloatingActionButtonItem(Icons.Default.Edit, "Edit") {
                update = true
                expand = false
            }
            ShuXian()
            FloatingActionButtonItem(Icons.Default.Delete, "Delete") {
                delete = true
                expand = false
            }
//            ShuXian()
//            FloatingActionButtonItem(Icons.Default.Refresh, "Refresh") {
//                expand = false
//                RoomViewModel.updateParts()
//            }
            ShuXian()
            FloatingActionButtonItem(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                "KeyboardArrowRight"
            ) {
                expand = false
            }
        } else {
            FloatingActionButtonItem(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                "expand"
            ) { expand = true }
        }
    }
}

@Composable
private fun ShuXian() {
    Spacer(modifier = Modifier.width(2.dp))
//    Divider(
//        modifier = Modifier
//            .width(1.dp)
//            .fillMaxHeight(),
//        color = Color.Black
//    )
    Spacer(modifier = Modifier.width(2.dp))
}

@Composable
private fun FloatingActionButtonItem(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            modifier = Modifier
                .background(ThemeColor)
                .fillMaxSize(),
            imageVector = icon,
            contentDescription = contentDescription
        )
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
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "id"
        )
        Text(
            fontSize = 20.sp,
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "传感器序列号"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.25f)
                .height(30.dp)
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
            items(StateViewModel.PARTS) { device ->
                Parts(device)
            }
        }
    }
}

@Composable
private fun Parts(parts: MutableList<Parts>) {
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
            parts.forEach {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(0.5f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = it.partsName
                    )
                    Text(
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(0.3f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = it.serialNumber.toString()
                    )
                    Text(
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(0.25f)
                            .border(1.dp, Color.Black)
                            .wrapContentSize(Alignment.Center),
                        text = it.type.sensorType()
                    )
                }
            }
        }
    }
}


