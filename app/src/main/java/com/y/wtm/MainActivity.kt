package com.y.wtm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.rememberNavController
import com.y.wtm.config.Config
import com.y.wtm.room.DataBase
import com.y.wtm.viewmodel.NavHostViewModel
import com.y.wtm.viewmodel.RoomViewModel
import com.y.wtm.viewmodel.WirelessTemperatureMeasurementApp
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException


const val TAG = "无线温湿度监测"

var WIDTH = 0f
var HEIGHT = 0f

//一天时间少1毫秒
const val DAY: Long = 86_399_999L
//一个月的时间是多少毫秒
const val MONTH: Long = 2_592_000_000L


class MainActivity : ComponentActivity() {
    private val database: DataBase by lazy { DataBase.initDataBase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        // enableEdgeToEdge()
        Config.initialize(applicationContext)
        RoomViewModel.initRoomViewModel(database = database)
        setContent {
           // ProfileInstaller.writeProfile(this)
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
        RoomViewModel.closeDataBase()
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

/**
 *获取本机IP
 *
 * @return
 */
fun getIpAddressString(): String {
    try {
        val enNetI = NetworkInterface
            .getNetworkInterfaces()
        while (enNetI.hasMoreElements()) {
            val netI = enNetI.nextElement()
            val enumIpAddr = netI
                .getInetAddresses()
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress = enumIpAddr.nextElement()
                if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress()) {
                    return inetAddress.getHostAddress()
                }
            }
        }
    } catch (e: SocketException) {
        e.printStackTrace()
    }
    return ""
}

