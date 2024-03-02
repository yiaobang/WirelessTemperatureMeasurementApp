package com.y.wtm.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import com.y.wtm.config.Config.readConfig
import com.y.wtm.config.Config.writeConfig
import com.y.wtm.config.baudRateParameter
import com.y.wtm.config.dataBitsParameter
import com.y.wtm.config.driverTypeParameter
import com.y.wtm.config.flowParameter
import com.y.wtm.config.parityParameter
import com.y.wtm.config.serialNumbersParameter
import com.y.wtm.config.stopBitsParameter
import com.y.wtm.config.usbNumbersParameter
import com.y.wtm.data.driver.DriverType
import com.y.wtm.data.driver.DriverType.COM
import com.y.wtm.data.driver.DriverType.USB
import com.y.wtm.data.initDriver
import com.y.wtm.ui.Parameter
import com.y.wtm.ui.components.TopBar
import com.y.wtm.viewmodel.StateViewModel

@Composable
fun WirelessReceiving() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("无线接收模块参数") }) { paddingValues ->
        WirelessReceivingContent(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WirelessReceivingContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Version()
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(readConfig(driverTypeParameter.title)) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text(driverTypeParameter.title) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                //  colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                for ((index, selectionOption) in driverTypeParameter.parameters.withIndex()) {
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            writeConfig(driverTypeParameter.title, selectedOptionText)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    if (index != driverTypeParameter.parameters.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
        }
        when (DriverType.driverType(selectedOptionText)) {
            COM -> {
                Parameter(serialNumbersParameter)
                Parameter(baudRateParameter)
                Parameter(dataBitsParameter)
                Parameter(stopBitsParameter)
                Parameter(parityParameter)
                Parameter(flowParameter)
            }

            USB -> {
                Parameter(usbNumbersParameter)
                Parameter(baudRateParameter)
                Parameter(dataBitsParameter)
                Parameter(stopBitsParameter)
                Parameter(parityParameter)
            }
        }
        Button(onClick = {
            initDriver()
        }) {
            Text(text = "确认")
        }
    }
}

@Composable
private fun Version() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(fontSize = 30.sp, text = "软件版本号: ")
            Text(fontSize = 30.sp, text = StateViewModel.SOFT.ifEmpty { "未知" })
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(fontSize = 30.sp, text = "硬件版本号: ")
            Text(fontSize = 30.sp, text = StateViewModel.HARD.ifEmpty { "未知" })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Parameter(par: Parameter) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(readConfig(par.title)) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(par.title) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            //  colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for ((index, selectionOption) in par.parameters.withIndex()) {
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        writeConfig(par.title, selectedOptionText)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
                if (index != par.parameters.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}




