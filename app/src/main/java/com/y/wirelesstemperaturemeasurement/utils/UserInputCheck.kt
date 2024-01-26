package com.y.wirelesstemperaturemeasurement.utils

fun String.isNumber(): Boolean {
    return try {
        if (this == "") {
            return true
        }
        if(this.toUInt() == 0.toUInt()){
            false
        }else{
            true
        }
    } catch (e: Exception) {
        false
    }
}

fun String.isID(): Boolean {
    return try {
        if (this == "") {
            return true
        }
        if(this.toUShort()== 0.toUShort()){
            false
        }else{
            true
        }
    } catch (e: Exception) {
        false
    }
}