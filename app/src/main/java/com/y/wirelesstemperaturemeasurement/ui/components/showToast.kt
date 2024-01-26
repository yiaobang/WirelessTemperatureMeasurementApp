package com.y.wirelesstemperaturemeasurement.ui.components

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}