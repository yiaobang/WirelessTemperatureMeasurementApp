package com.y.wtm.data.driver

enum class DriverType {
    COM,
    USB;

    companion object {

        fun driverType(type: DriverType): String = when (type) {
            USB -> "USB"
            else -> "COM"
        }

        fun driverType(type: String): DriverType = when (type) {
            "USB" -> USB
            else -> COM
        }
    }
}
