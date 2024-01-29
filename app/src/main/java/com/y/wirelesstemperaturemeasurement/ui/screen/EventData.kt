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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.y.wirelesstemperaturemeasurement.room.ShowEvent
import com.y.wirelesstemperaturemeasurement.room.event
import com.y.wirelesstemperaturemeasurement.room.isID
import com.y.wirelesstemperaturemeasurement.room.temp
import com.y.wirelesstemperaturemeasurement.room.toDate
import com.y.wirelesstemperaturemeasurement.room.toDateTime
import com.y.wirelesstemperaturemeasurement.room.voltageRH
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.viewmodel.RoomViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel


@Composable
fun EventData() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = { SearchEvent() },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { TopBar("事件") }) { paddingValues ->
        Content(paddingValues)
    }
}

@Composable
private fun Content(paddingValues: PaddingValues) {
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
            items(StateViewModel.event) {
                Event(it)
            }
        }
    }
}

@Composable
private fun Title() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.1f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "id"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.18f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "设备名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "测温点名称"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "温度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.15f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "电压/湿度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "级别"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "描述"
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
private fun Event(e: ShowEvent) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.1f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.id.toString()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.18f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.deviceName
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.partsName
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.temp()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.15f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.voltageRH()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.eventLevel.event()
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.12f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.eventMsg
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.25f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = e.time.toDateTime()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchEvent() {
    var expand by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var deviceName by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var event by remember { mutableStateOf("") }
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
                    text = "选择事件过滤条件"
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
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            value = event,
                            onValueChange = {},
                            label = { Text(text = "事件级别(可选)", fontSize = 10.sp) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "所有级别") },
                                onClick = {
                                    event = "所有级别"
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(text = "告警") },
                                onClick = {
                                    event = "告警"
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(text = "报警") },
                                onClick = {
                                    event = "报警"
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
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
                        RoomViewModel.selectEvent(
                            if (id.isID()) id.toLong() else null,
                            deviceName,
                            event.event(),
                            start.selectedDateMillis,
                            end.selectedDateMillis
                        )
                        expand = false
                        keyboardController?.hide()
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