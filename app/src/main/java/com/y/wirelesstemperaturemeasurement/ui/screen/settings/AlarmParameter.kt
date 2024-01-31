package com.y.wirelesstemperaturemeasurement.ui.screen.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.GridCards
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel

@Composable
fun AlarmWarningParameter() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("预警告警参数") }) { paddingValues ->
        AlarmParameterContent(paddingValues)
    }
}

@Composable
private fun AlarmParameterContent(paddingValues: PaddingValues) {
    GridCards(paddingValues, NavHostViewModel.alarmWarningParameter)
}
