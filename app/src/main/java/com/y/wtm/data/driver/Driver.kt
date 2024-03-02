package com.y.wtm.data.driver

import java.io.Closeable

interface Driver : Closeable {
    fun open()
    fun openAfter()
    fun isOpen():Boolean
    fun write(bytes: ByteArray)
}


