package com.y.wirelesstemperaturemeasurement.ui.screen.eventData

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.screen.EventContent

@Composable
fun Alarm() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("告警") }) { paddingValues ->
        AlarmContent(paddingValues)
    }
}

@Composable
fun AlarmContent(paddingValues: PaddingValues) {

}
