package com.y.wirelesstemperaturemeasurement.ui.screen.uploadData

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.screen.EventContent

@Composable
fun Modbus() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("Modbus") }) { paddingValues ->
        ModbusContent(paddingValues)
    }
}

@Composable
fun ModbusContent(paddingValues: PaddingValues) {

}
