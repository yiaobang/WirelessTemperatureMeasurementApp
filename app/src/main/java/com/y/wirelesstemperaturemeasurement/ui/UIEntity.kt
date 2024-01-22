package com.y.wirelesstemperaturemeasurement.ui

data class CardInfo(
    val route: String,
    val image: Int,
    val text: String
)
data class Parameter(
    val title:String,
    val parameters: List<String>
)