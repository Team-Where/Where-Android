package com.sooum.core.notification.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.sooum.core.alarm.AlarmMaker
import java.time.LocalDateTime
import java.time.ZoneId

class LocalAlarmManager(
    private val context: Context
) : AlarmMaker {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    private fun createPendingIntent(meetId: Int, alarmType: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("meetId", meetId)
            putExtra("alarm_type", alarmType)
        }
        val requestCode = meetId * 10 + alarmType

        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun makeAlarm(
        meetId: Int,
        standardData: LocalDateTime
    ) {
        val alarmTimes = listOf(
            standardData.minusHours(24),
            standardData.minusHours(1)
        )

        alarmTimes.forEachIndexed { index, time ->
            val triggerMillis = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val nowMillis = System.currentTimeMillis()

            if (triggerMillis <= nowMillis) {
                // 이미 지난 알람은 무시
                return@forEachIndexed
            }

            val alarmType = index + 1

            val pendingIntent = createPendingIntent(meetId, alarmType)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        triggerMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerMillis,
                    pendingIntent
                )
            }
        }
    }

    override fun cancelAlarm(meetId: Int) {
        repeat(2) { index ->
            val alarmType = index + 1
            val pendingIntent = createPendingIntent(meetId, alarmType)
            alarmManager.cancel(pendingIntent)
        }
    }
}