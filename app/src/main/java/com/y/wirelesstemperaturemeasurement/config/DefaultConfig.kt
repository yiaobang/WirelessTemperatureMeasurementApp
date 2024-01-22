package com.y.wirelesstemperaturemeasurement.config

import com.y.wirelesstemperaturemeasurement.data.listener.serialPortNames
import com.y.wirelesstemperaturemeasurement.ui.Parameter

val serialNumbersParameter = Parameter("串口号", serialPortNames)
val baudRateParameter = Parameter("波特率", listOf("19200", "115200"))
val dataBitsParameter = Parameter("数据位", listOf("5", "6", "7", "8"))
val stopBitsParameter = Parameter("停止位", listOf("1", "1.5", "2"))
val parityParameter = Parameter("校验", listOf("无", "奇校验", "偶校验", "标记校验", "空格校验"))
val flowParameter = Parameter("流控", listOf("无", "RTS/CTS", "DSR/DTR", "ON/OFF"))