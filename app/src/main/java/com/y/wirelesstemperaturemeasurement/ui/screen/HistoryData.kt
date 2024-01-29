package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.room.HistoryData
import com.y.wirelesstemperaturemeasurement.room.isID
import com.y.wirelesstemperaturemeasurement.room.temp
import com.y.wirelesstemperaturemeasurement.room.toDate
import com.y.wirelesstemperaturemeasurement.room.toDateTime
import com.y.wirelesstemperaturemeasurement.room.voltageRH
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.viewmodel.RoomViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@Composable
fun HistoryData() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = { SearchHistory() },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { TopBar("历史数据") }) { paddingValues ->
        HistoryContent(paddingValues)
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
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "id"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "设备名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "测温点名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.17f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "温度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "电压/湿度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.25f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "时间"
        )
    }
}

@Composable
fun HistoryContent(paddingValues: PaddingValues) {
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
            items(StateViewModel.historyData) {
                Data(it)
            }
        }
    }
}

@Composable
private fun Data(data: HistoryData) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.id.toString()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.deviceName
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.partsName
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.17f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.temp()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.voltageRH()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.25f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = data.time.toDateTime()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchHistory() {
    var expand by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var deviceName by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var startString by remember {
        mutableStateOf("")
    }
    var endString by remember {
        mutableStateOf("")
    }
    val start = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(), // epoch/unix timestamp
        initialDisplayMode = DisplayMode.Input,
    )
    val end = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(), // epoch/unix timestamp
        initialDisplayMode = DisplayMode.Input,
    )
    IconButton(onClick = { expand = true }) {
        Icon(
            modifier = Modifier
                .background(ThemeColor)
                .size(50.dp),
            imageVector = Icons.Default.Search,
            contentDescription = "Search"
        )
    }
    if (expand) {
        AlertDialog(
            onDismissRequest = {
                expand = false
                keyboardController?.hide()
            },
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    text = "选择数据过滤条件"
                )
            },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "设备名称(可选)", fontSize = 10.sp) },
                        value = deviceName,
                        onValueChange = { deviceName = it },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "测温点ID(可选)", fontSize = 10.sp) },
                        value = id,
                        onValueChange = {
                            if (it.isID()) {
                                id = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Box {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "开始时间(可选)", fontSize = 10.sp) },
                            value = startString,
                            readOnly = true,
                            onValueChange = {}
                        )
                        var startShow by remember { mutableStateOf(false) }
                        if (startShow) {
                            AlertDialog(
                                onDismissRequest = {
                                    startShow = false
                                    keyboardController?.hide()
                                },
                                title = {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentSize(Alignment.Center),
                                        text = "开始时间"
                                    )
                                },
                                text = {
                                    DatePicker(
                                        state = start,
                                        showModeToggle = false,
                                        headline = null,
                                        title = null,
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            startString =
                                                if (start.selectedDateMillis == null) "" else start.selectedDateMillis!!.toDate()
                                            startShow = false
                                        }
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            startShow = false
                                        }
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = { startShow = true }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "start"
                            )
                        }
                    }
                    Box {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "截至时间(可选)", fontSize = 10.sp) },
                            value = endString,
                            readOnly = true,
                            onValueChange = {}
                        )
                        var endShow by remember {
                            mutableStateOf(false)
                        }
                        if (endShow) {
                            AlertDialog(
                                onDismissRequest = {
                                    endShow = false
                                    keyboardController?.hide()
                                },
                                title = {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentSize(Alignment.Center),
                                        text = "开始时间"
                                    )
                                },
                                text = {
                                    DatePicker(
                                        state = end,
                                        showModeToggle = false,
                                        headline = null,
                                        title = null,
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            endString =
                                                if (end.selectedDateMillis == null) "" else end.selectedDateMillis!!.toDate()
                                            endShow = false
                                        }
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            endShow = false
                                        }
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = { endShow = true }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "end"
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(start.selectedDateMillis!=null && end.selectedDateMillis!=null && start.selectedDateMillis!! > end.selectedDateMillis!!){
                            showToast(context,"开始时间不能大于截至时间")
                        }else{
                            RoomViewModel.selectHistoryData(
                                if (id=="")null else id.toLong(),
                                deviceName,
                                start.selectedDateMillis,
                                end.selectedDateMillis
                            )
                            expand = false
                            keyboardController?.hide()
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        expand = false
                        keyboardController?.hide()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

