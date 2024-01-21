package com.y.wirelesstemperaturemeasurement.ui.screen.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.screen.EventContent

@Composable
fun WarningParameter() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("预警参数") }) { paddingValues ->
        WarningParameterContent(paddingValues)
    }
}

@Composable
fun WarningParameterContent(paddingValues: PaddingValues) {

}
