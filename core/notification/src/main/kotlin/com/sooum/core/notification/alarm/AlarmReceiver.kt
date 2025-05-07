package com.sooum.core.notification.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sooum.core.notification.NotificationUtil
import com.sooum.core.notification.di.LocalTool
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val MEET_ID = "meetId"
        const val ALARM_TYPE = "alarm_type"
    }

    @Inject
    @LocalTool
    lateinit var localNotificationUtil: NotificationUtil

    override fun onReceive(context: Context, intent: Intent?) {

        if (isAlarmMeet(intent)) {
            val meetId = intent?.getIntExtra(MEET_ID, -1) ?: -1
            val alarmType = intent?.getIntExtra(ALARM_TYPE, -1) ?: -1
            if (meetId >= 0 && alarmType in (1..2)) {
                val msg = if (alarmType == 1) {
                    "24시간 전 입니다."
                } else {
                    "1시간 전 입니다."
                }
                localNotificationUtil.makeNotify {
                    setContentTitle("Alarm")
                    setContentText("${meetId}가 $msg")
                }
            }
        }
    }

    private fun isAlarmMeet(
        intent: Intent?
    ): Boolean {
        return intent?.action?.let { action ->
            Regex("ALARM_\\d+_\\d+").matches(action)
        } ?: false
    }
}