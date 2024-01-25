package com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping

import android.util.Log
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.config.sensorType
import com.y.wirelesstemperaturemeasurement.room.PARTS_DAO
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.utils.sensorType
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UpdateParts(isDialogVisible: Boolean, update: (b: Boolean) -> Unit) {
    var oldSerialNumber by remember { mutableStateOf("") }
    var newSerialNumber by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(sensorType[0]) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var expanded by remember { mutableStateOf(false) }
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
                        text = "修改测温点"
                    )
                },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "旧的传感器序列号", fontSize = 10.sp) },
                            value = oldSerialNumber,
                            onValueChange = { oldSerialNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "新的传感器序列号", fontSize = 10.sp) },
                            value = newSerialNumber,
                            onValueChange = { newSerialNumber = it },
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
                                label = { Text(text = "新的传感器类型", fontSize = 10.sp) },
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
                            try {
                                PARTS_DAO.updateParts(
                                    oldSerialNumber.trim().toInt(),
                                    newSerialNumber.trim().toInt(),
                                    type.sensorType()
                                )
                                StateViewModel.updateParts()
                                showToast(context, "传感器更换成功")
                            } catch (e: Exception) {
                                showToast(context, "传感器更换失败")
                                Log.e(TAG, "AddParts: ", e)
                            }
                            update(false)
                            keyboardController?.hide()
                            // Handle user input here
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