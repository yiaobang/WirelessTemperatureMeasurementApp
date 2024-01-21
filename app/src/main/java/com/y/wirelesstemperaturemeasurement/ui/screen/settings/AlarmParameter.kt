package com.y.wirelesstemperaturemeasurement.ui.screen.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar

@Composable
fun AlarmParameter() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("告警参数") }) { paddingValues ->
        AlarmParameterContent(paddingValues)
    }
}

@Composable
fun AlarmParameterContent(paddingValues: PaddingValues) {

}
