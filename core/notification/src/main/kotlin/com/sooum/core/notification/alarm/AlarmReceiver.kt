package com.sooum.core.notification.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sooum.core.notification.AlarmOption
import com.sooum.core.notification.NotificationUtil
import com.sooum.core.notification.di.LocalTool
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PREFIX = "WHERE_ALARM"
        const val MEET_ID = "meetId"
        const val MEET_NAME = "meetName"
        const val ALARM_TYPE = "alarm_type"
    }

    @Inject
    @LocalTool
    lateinit var localNotificationUtil: NotificationUtil

    @Inject
    lateinit var alarmOption: AlarmOption

    override fun onReceive(context: Context, intent: Intent?) {

        GlobalScope.launch() {
            if (isAlarmMeet(intent) && alarmOption.notificationAllowed()) {
                val meetId = intent?.getIntExtra(MEET_ID, -1) ?: -1
                val meetName = intent?.getStringExtra(MEET_NAME) ?: ""
                val alarmType = intent?.getIntExtra(ALARM_TYPE, -1) ?: -1

                if (meetId >= 0 && alarmType in (1..2) && meetName.isNotEmpty()) {
                    val msg = if (alarmType == 1) {
                        "24시간 전 입니다."
                    } else {
                        "1시간 전 입니다."
                    }
                    val newIntent = alarmOption.makeIntent().apply {
                        action = intent?.action
                        intent?.extras?.let { putExtras(it) }
                    }
                    val pendingIntent: PendingIntent =
                        PendingIntent.getActivity(
                            context,
                            0,
                            newIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    localNotificationUtil.makeNotify {
                        setContentTitle("모임 알림")
                        setContentText("$meetName $msg")
                        setContentIntent(pendingIntent)
                        setAutoCancel(true)
                        setGroup("WHERE_MEET_GROUP")
                        setGroupSummary(true)
                    }
                }
            }
        }
    }

    private fun isAlarmMeet(
        intent: Intent?
    ): Boolean {
        return intent?.action?.let { action ->
            Regex("${ACTION_PREFIX}_\\d+_\\d+").matches(action)
        } ?: false
    }
}