package com.y.wtm.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.y.wtm.ui.components.GridCards
import com.y.wtm.ui.components.TopBar
import com.y.wtm.viewmodel.NavHostViewModel

@Composable
fun Menu() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("菜单") }) { paddingValues ->
        GridCards(paddingValues,  NavHostViewModel.menuCards)
    }
}
