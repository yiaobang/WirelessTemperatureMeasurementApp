package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.ui.components.GridCards
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.viewmodel.DataViewModel

@Composable
fun Menu() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("菜单") }) { paddingValues ->
        GridCards(paddingValues,  DataViewModel.menuCards)
    }
}
