package com.y.wirelesstemperaturemeasurement.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.y.wirelesstemperaturemeasurement.TAG

object StateViewModel : ViewModel() {
    var audible by mutableStateOf(true)
        private set
    var sensor by mutableIntStateOf(0)
    var warn by mutableIntStateOf(0)
    var error by mutableIntStateOf(0)
    //从配置文件中读取状态
    init {
        Log.d(TAG, "ViewModel初始化+++++++++++++++++++")
    }

    fun updateAudible() {
        audible = !audible
    }

    fun updateAudible(b: Boolean) {
        audible = b
    }
}