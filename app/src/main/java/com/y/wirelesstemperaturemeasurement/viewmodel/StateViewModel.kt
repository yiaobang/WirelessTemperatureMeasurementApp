package com.y.wirelesstemperaturemeasurement.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.y.wirelesstemperaturemeasurement.TAG

object StateViewModel : ViewModel() {
    //串口状态
    var SerialPort by mutableStateOf(false)
    //软件版本
    var SOFT by mutableStateOf("")
    //硬件版本
    var HARD by mutableStateOf("")

    var audible by mutableStateOf(true)
        private set
    var sensor by mutableIntStateOf(0)
    var warn by mutableIntStateOf(0)
    var error by mutableIntStateOf(0)
    init {
        Log.d(TAG, "ViewModel: ")
    }
    fun updateAudible() {
        audible = !audible
    }

    fun updateAudible(b: Boolean) {
        audible = b
    }
}