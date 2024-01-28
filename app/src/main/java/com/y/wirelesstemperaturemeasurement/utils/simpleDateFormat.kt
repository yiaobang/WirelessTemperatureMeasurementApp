package com.y.wirelesstemperaturemeasurement.utils

import java.text.SimpleDateFormat
import java.util.Date

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
fun now(): String = simpleDateFormat.format(Date())
fun Long.time(): String = simpleDateFormat.format(Date(this))

