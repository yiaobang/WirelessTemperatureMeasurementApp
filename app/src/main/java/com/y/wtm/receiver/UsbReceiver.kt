package com.y.wtm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.widget.Toast
import com.y.wtm.ACTION_USB_PERMISSION


/**
 * Usb 授权
 *
 * @constructor Create empty Usb receiver
 */
class UsbReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == ACTION_USB_PERMISSION) {
            synchronized(this) {
                context?.unregisterReceiver(this)
                val device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE) as UsbDevice?
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    //授权成功

                } else {
                    //用户拒绝
                    Toast.makeText(context, "已拒绝访问USB权限", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}