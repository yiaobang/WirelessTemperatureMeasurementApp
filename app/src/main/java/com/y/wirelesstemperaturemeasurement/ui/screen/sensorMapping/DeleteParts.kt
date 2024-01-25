package com.y.wirelesstemperaturemeasurement.ui.screen.sensorMapping

import android.util.Log
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.room.PARTS_DAO
import com.y.wirelesstemperaturemeasurement.ui.components.showToast
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteParts(isDialogVisible: Boolean, update: (b: Boolean) -> Unit) {
    var serialNumber by remember { mutableStateOf("") }
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
                        text = "删除测温点"
                    )
                },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "传感器序列号", fontSize = 10.sp) },
                            value = serialNumber,
                            onValueChange = { serialNumber = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            try {
                                PARTS_DAO.delete(serialNumber.trim().toInt())
                                StateViewModel.updateParts()
                                showToast(context, "删除成功")
                            } catch (e: Exception) {
                                showToast(context, "删除失败")
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