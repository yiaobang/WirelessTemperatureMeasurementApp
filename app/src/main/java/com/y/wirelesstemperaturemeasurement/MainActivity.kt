package com.y.wirelesstemperaturemeasurement

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.y.wirelesstemperaturemeasurement.data.listener.connection
import com.y.wirelesstemperaturemeasurement.room.DataBase
import com.y.wirelesstemperaturemeasurement.ui.theme.WirelessTemperatureMeasurementAppTheme

const val TAG = "无线温湿度监测"
lateinit var dataBase: DataBase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContent {
            WirelessTemperatureMeasurementAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        initApp(application)
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
        closeApp()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

}