package com.y.wirelesstemperaturemeasurement.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hello() {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(), // epoch/unix timestamp
        initialDisplayMode = DisplayMode.Input,
    )

    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        headline = null,
        title = null,
    )
}

@Preview
@Composable
fun TestPr() {
    Hello()
}