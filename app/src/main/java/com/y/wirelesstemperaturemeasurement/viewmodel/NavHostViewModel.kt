package com.y.wirelesstemperaturemeasurement.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.y.wirelesstemperaturemeasurement.R
import com.y.wirelesstemperaturemeasurement.TAG
import com.y.wirelesstemperaturemeasurement.ui.CardInfo
import com.y.wirelesstemperaturemeasurement.ui.screen.EventData
import com.y.wirelesstemperaturemeasurement.ui.screen.HistoryData
import com.y.wirelesstemperaturemeasurement.ui.screen.Home
import com.y.wirelesstemperaturemeasurement.ui.screen.Menu
import com.y.wirelesstemperaturemeasurement.ui.screen.SensorMap
import com.y.wirelesstemperaturemeasurement.ui.screen.Settings
import com.y.wirelesstemperaturemeasurement.ui.screen.UploadData
import com.y.wirelesstemperaturemeasurement.ui.screen.eventData.Alarm
import com.y.wirelesstemperaturemeasurement.ui.screen.eventData.Warning
import com.y.wirelesstemperaturemeasurement.ui.screen.settings.AlarmParameter
import com.y.wirelesstemperaturemeasurement.ui.screen.settings.WarningParameter
import com.y.wirelesstemperaturemeasurement.ui.screen.settings.WirelessReceiving
import com.y.wirelesstemperaturemeasurement.ui.screen.uploadData.MQTT
import com.y.wirelesstemperaturemeasurement.ui.screen.uploadData.Modbus

@Composable
fun WirelessTemperatureMeasurementApp() {
    NavHost(navController = NavHostViewModel.navHostController, startDestination = "Home") {
        composable("Home") { Home() }
        composable("Menu") { Menu() }

        composable("Menu/EventData") { EventData() }
        composable("Menu/UploadData") { UploadData() }
        composable("Menu/HistoryData") { HistoryData() }
        composable("Menu/SensorMap") { SensorMap() }
        composable("Menu/Settings") { Settings() }

        composable("Menu/EventData/Warning") { Warning() }
        composable("Menu/EventData/Alarm") { Alarm() }

        composable("Menu/UploadData/MQTT") { MQTT() }
        composable("Menu/UploadData/Modbus") { Modbus() }

        composable("Menu/Settings/WirelessReceiving") { WirelessReceiving() }
        composable("Menu/Settings/WarningParameter") { WarningParameter() }
        composable("Menu/Settings/AlarmParameter") { AlarmParameter() }
    }
}

object NavHostViewModel : ViewModel() {
    lateinit var navHostController: NavHostController
        private set
    fun navHost(navHostController: NavHostController) {
        this.navHostController = navHostController
    }
    fun navigate(route: String) {
        navHostController.navigate(route)
    }
    fun popBackStack() {
        navHostController.popBackStack()
    }
    init {
        Log.d(TAG, "ViewModel: ")
    }
    //菜单栏内容
    val menuCards = arrayOf(
        CardInfo("Menu/EventData", R.drawable.logo_64, "事件"),
        CardInfo("Menu/HistoryData", R.drawable.logo_64, "历史数据"),
        CardInfo("Menu/SensorMap", R.drawable.bbar_point, "传感器映射"),
        CardInfo("Menu/UploadData", R.drawable.logo_64, "上传"),
        CardInfo("Menu/Settings", R.drawable.logo_64, "设置")
    )
    val eventCards = arrayOf(
        CardInfo("Menu/EventData/Warning", R.drawable.logo_64, "预警"),
        CardInfo("Menu/EventData/Alarm", R.drawable.logo_64, "告警")
    )
    val upLoadCards = arrayOf(
        CardInfo("Menu/UploadData/MQTT", R.drawable.logo_64, "MQTT"),
        CardInfo("Menu/UploadData/Modbus", R.drawable.logo_64, "Modbus")
    )
    val settingsCards = arrayOf(
        CardInfo("Menu/Settings/WirelessReceiving", R.drawable.logo_64, "无线接收模块设置"),
        CardInfo("Menu/Settings/WarningParameter", R.drawable.logo_64, "预警参数设置"),
        CardInfo("Menu/Settings/AlarmParameter", R.drawable.logo_64, "告警参数设置")
    )
}