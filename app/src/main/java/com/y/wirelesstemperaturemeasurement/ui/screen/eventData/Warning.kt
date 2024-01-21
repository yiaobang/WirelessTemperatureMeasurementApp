package com.y.wirelesstemperaturemeasurement.ui.screen.eventData

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.screen.EventContent

@Composable
fun Warning() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("预警") }) { paddingValues ->
        WarningContent(paddingValues)
    }
}

@Composable
fun WarningContent(paddingValues: PaddingValues) {

}
