package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar

@Composable
fun SensorMap() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar( "设备映射") }) { paddingValues ->
        SensorMapContent(paddingValues)
    }
}

@Composable
fun SensorMapContent(paddingValues: PaddingValues) {

}
