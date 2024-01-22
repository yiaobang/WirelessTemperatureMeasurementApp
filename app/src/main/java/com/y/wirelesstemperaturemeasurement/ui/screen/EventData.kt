package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.GridCards
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel


@Composable
fun EventData() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("事件") }) { paddingValues ->
        EventContent(paddingValues)
    }
}

@Composable
fun EventContent(paddingValues: PaddingValues) {
    GridCards(paddingValues,NavHostViewModel.eventCards)
}