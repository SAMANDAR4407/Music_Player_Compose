package uz.gita.mymusicapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import uz.gita.mymusicapp.data.model.CommandEnum
import uz.gita.mymusicapp.utils.manageMusicService

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:44
 */

class CallReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            when (telephonyManager.callState) {
                TelephonyManager.CALL_STATE_RINGING -> {
                        // Handle incoming call and pause music
                        manageMusicService(context, CommandEnum.PAUSE)
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                        // Handle outgoing call and pause music
                        manageMusicService(context, CommandEnum.PAUSE)
                }

                TelephonyManager.CALL_STATE_IDLE -> {
                        // Handle phone call is end
                }
            }
        }
    }
}