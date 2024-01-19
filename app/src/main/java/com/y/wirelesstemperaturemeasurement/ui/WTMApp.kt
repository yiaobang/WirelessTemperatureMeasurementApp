package com.y.wirelesstemperaturemeasurement.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun WirelessTemperatureMeasurementApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Top() },
        bottomBar = { Bottom() }
    ) { padd -> Body(padd)
    }
}

@@Composable
fun Top() {

}

@@Composable
fun Body(padd: PaddingValues) {

}

@@Composable
fun Bottom() {

}