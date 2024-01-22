package com.y.wirelesstemperaturemeasurement

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.rememberNavController
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.WirelessTemperatureMeasurementApp

const val TAG = "无线温湿度监测"

var WIDTH = 0f
var HEIGHT = 0f

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        // enableEdgeToEdge()
        initApp(applicationContext)
        setContent {
            NavHostViewModel.navHost(rememberNavController())
            ReadWidthHeight()
            WirelessTemperatureMeasurementApp()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        closeApp()
        Log.d(TAG, "onDestroy: ")
    }

}

@Composable
fun ReadWidthHeight() {
    //获取屏幕宽高
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.toDp()
    }
    val screenHeightDp = with(LocalDensity.current) {
        displayMetrics.heightPixels.toDp()
    }
    WIDTH = screenWidthDp.value
    HEIGHT = screenHeightDp.value
    Log.d(TAG, "ReadWidthHeight: 宽:${WIDTH}dp 高:${HEIGHT}dp")
}

