package com.y.wtm.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.y.wtm.R
import com.y.wtm.ui.CardInfo
import com.y.wtm.ui.screen.EventData
import com.y.wtm.ui.screen.HistoryData
import com.y.wtm.ui.screen.Home
import com.y.wtm.ui.screen.Menu
import com.y.wtm.ui.screen.SensorMap
import com.y.wtm.ui.screen.Settings
import com.y.wtm.ui.screen.UploadData
import com.y.wtm.ui.screen.settings.AlarmWarningParameter
import com.y.wtm.ui.screen.settings.WirelessReceiving
import com.y.wtm.ui.screen.settings.sensorParameter.Temperature
import com.y.wtm.ui.screen.settings.sensorParameter.TemperatureHumidity
import com.y.wtm.ui.screen.uploadData.MQTT
import com.y.wtm.ui.screen.uploadData.Modbus

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


        composable("Menu/UploadData/MQTT") { MQTT() }
        composable("Menu/UploadData/Modbus") { Modbus() }

        composable("Menu/Settings/WirelessReceiving") { WirelessReceiving() }
        composable("Menu/Settings/AlarmWarningParameter") { AlarmWarningParameter() }
        composable("Menu/Settings/AlarmWarningParameter/Temperature") { Temperature() }
        composable("Menu/Settings/AlarmWarningParameter/TemperatureHumidity") { TemperatureHumidity() }
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

    //菜单栏内容
    val menuCards = arrayOf(
        CardInfo("Menu/EventData", R.drawable.logo_64, "事件"),
        CardInfo("Menu/HistoryData", R.drawable.logo_64, "历史数据"),
        CardInfo("Menu/SensorMap", R.drawable.logo_64, "设备映射"),
        CardInfo("Menu/UploadData", R.drawable.logo_64, "上传"),
        CardInfo("Menu/Settings", R.drawable.logo_64, "设置")
    )

    //上传
    val upLoadCards = arrayOf(
        CardInfo("Menu/UploadData/MQTT", R.drawable.logo_64, "MQTT"),
        CardInfo("Menu/UploadData/Modbus", R.drawable.logo_64, "Modbus")
    )

    //设置
    val settingsCards = arrayOf(
        CardInfo("Menu/Settings/WirelessReceiving", R.drawable.logo_64, "无线接收模块设置"),
        CardInfo("Menu/Settings/AlarmWarningParameter", R.drawable.logo_64, "预警告警参数设置")
    )
    //参数设置
    val alarmWarningParameter = arrayOf(
        CardInfo("Menu/Settings/AlarmWarningParameter/Temperature", R.drawable.logo_64, "设备温度传感器"),
        CardInfo("Menu/Settings/AlarmWarningParameter/TemperatureHumidity", R.drawable.logo_64, "环境温湿度传感器")
    )
}