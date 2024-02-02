package com.y.wtm.ui.screen.sensorMapping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import com.y.wtm.room.Parts
import com.y.wtm.room.isID
import com.y.wtm.room.sensorType
import com.y.wtm.ui.components.showToast
import com.y.wtm.viewmodel.RoomViewModel
import com.y.wtm.viewmodel.RoomViewModel.deleteParts

@Composable
fun DeleteParts(isDialogVisible: Boolean, update: (b: Boolean) -> Unit) {
    var id by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var partsInfo by remember { mutableStateOf(Parts(0, "", "", 0, 1)) }
    var confirmed by remember { mutableStateOf(false) }
    if (confirmed) {
        AlertDialog(
            onDismissRequest = {
                confirmed = false
                keyboardController?.hide()
            },
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    text = "删除确认"
                )
            },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        text = "要删除的测温点信息如下:\nID: ${partsInfo.id}\n设备名称: ${partsInfo.deviceName}\n测温点名称: ${partsInfo.partsName}\n传感器序列号: ${partsInfo.serialNumber}\n传感器类型: ${partsInfo.type.sensorType()} ",
                        fontSize = 10.sp
                    )
                }
            }, confirmButton = {
                TextButton(
                    onClick = {
                        RoomViewModel.delete(id.toInt())
                        showToast(context, "删除成功")
                        confirmed = false
                        keyboardController?.hide()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        confirmed = false
                        keyboardController?.hide()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


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
                        text = "删除测温点"
                    )
                },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "要删除的测温点ID", fontSize = 10.sp) },
                            value = id,
                            onValueChange = {
                                if (it.isID()) {
                                    id = it
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (id == "") {
                                showToast(context, "要删除的测温点ID不能为空")
                            } else {
                                deleteParts(
                                    id.toLong(),
                                    context
                                ) {
                                    partsInfo = it
                                    confirmed = true
                                }
                            }
                            //update(false)
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