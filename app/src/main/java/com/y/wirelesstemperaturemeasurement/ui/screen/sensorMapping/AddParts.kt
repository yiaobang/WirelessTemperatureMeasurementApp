package com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.config.sensorType
import com.y.wirelesstemperaturemeasurement.room.Parts
import com.y.wirelesstemperaturemeasurement.room.isID
import com.y.wirelesstemperaturemeasurement.room.isNumber
import com.y.wirelesstemperaturemeasurement.room.sensorType
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddParts(isDialogVisible: Boolean, update: (b: Boolean) -> Unit) {
    var id by remember { mutableStateOf("") }
    var deviceName by remember { mutableStateOf("") }
    var partsName by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(sensorType[0]) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = {
                    update(false)
                    keyboardController?.hide()
                },
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        text = "添加测温点"
                    )
                },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "ID", fontSize = 10.sp) },
                            value = id,
                            onValueChange = {
                                if (it.isID()) {
                                    id = it
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "设备名称", fontSize = 10.sp) },
                            value = deviceName,
                            onValueChange = { deviceName = it.trim() },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "测温点名称", fontSize = 10.sp) },
                            value = partsName,
                            onValueChange = { partsName = it.trim() },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "传感器序列号", fontSize = 10.sp) },
                            value = serialNumber,
                            onValueChange = {
                                if (it.isNumber()) {
                                    serialNumber = it
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
                                value = type,
                                onValueChange = {},
                                label = { Text(text = "传感器类型", fontSize = 10.sp) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                //  colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                for ((index, s) in sensorType.withIndex()) {
                                    DropdownMenuItem(
                                        text = { Text(s) },
                                        onClick = {
                                            type = s
                                            expanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                    if (index != sensorType.size - 1) {
                                        Divider()
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (id == "") {
                                showToast(context, "ID不能为空")
                            } else if (deviceName == "") {
                                showToast(context, "设备名称不能为空")
                            } else if (partsName == "") {
                                showToast(context, "测温点名称不能为空")
                            } else if (serialNumber == "") {
                                showToast(context, "传感器序列号不能为空")
                            } else {
                                RoomViewModel.addParts(
                                    Parts(
                                        id.toLong(),
                                        deviceName,
                                        partsName,
                                        serialNumber.toLong(),
                                        type.sensorType()
                                    ), context
                                )
                            }
                            keyboardController?.hide()
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            update(false)
                            keyboardController?.hide()
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

