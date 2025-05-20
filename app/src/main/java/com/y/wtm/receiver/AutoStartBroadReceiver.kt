package com.y.wtm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.y.wtm.MainActivity

/**
 *  App自启动
 * @author Y
 * @date 2024/03/01
 */
class AutoStartBroadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mainActivity = Intent(context, MainActivity::class.java)
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mainActivity)
    }
}