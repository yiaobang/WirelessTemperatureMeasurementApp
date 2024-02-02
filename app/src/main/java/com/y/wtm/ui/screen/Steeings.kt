package com.y.wtm.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wtm.ui.components.GridCards
import com.y.wtm.ui.components.TopBar
import com.y.wtm.viewmodel.NavHostViewModel

@Composable
fun Settings() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("设置") }) { paddingValues ->
        SettingsContent(paddingValues)
    }
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    GridCards(paddingValues, NavHostViewModel.settingsCards)
}
