package com.y.wirelesstemperaturemeasurement.config

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.y.wirelesstemperaturemeasurement.TAG

const val config_name = "WirelessTemperatureMeasurementAppConfig"

@SuppressWarnings("all")
object Config {
    private lateinit var context: Context
    private lateinit var read: SharedPreferences
    private lateinit var write: SharedPreferences.Editor
    fun initialize(context: Context) {
        this.context = context
        read = Config.context.getSharedPreferences(config_name, Context.MODE_PRIVATE)
        write = read.edit()
    }

    fun readConfig(key: String, def: String = "") = read.getString(key, def).toString()
    fun writeConfig(key: String, value: String) {
        Log.d(TAG, "writeConfig: $key = $value")
        write.putString(key, value)
        write.apply()
    }
}