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


    private fun createPendingIntent(
        meetId: Int,
        meetName: String,
        alarmType: Int
    ): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "ALARM_${meetId}_$alarmType"
            putExtra(AlarmReceiver.MEET_ID, meetId)
            putExtra(AlarmReceiver.MEET_NAME, meetName)
            putExtra(AlarmReceiver.ALARM_TYPE, alarmType)
        }
        val requestCode = meetId * 10 + alarmType

        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }


    override fun makeAlarm(
        meetId: Int,
        meetName: String,
        standardData: LocalDateTime
    ) {
        val alarmTimes = listOf(
            standardData.minusHours(24), // 1
            standardData.minusHours(1) // 2
        )

        alarmTimes.forEachIndexed { index, time ->
            val triggerMillis = time.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
            val nowMillis = System.currentTimeMillis()

            val alarmType = index + 1
            val pendingIntent = createPendingIntent(meetId, meetName, alarmType)
            alarmManager.cancel(pendingIntent)

            if (triggerMillis <= nowMillis) {
                return@forEachIndexed
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerMillis,
                    pendingIntent
                )
            }
        }
    }

    override fun cancelAlarm(
        meetId: Int,
        meetName: String
    ) {
        repeat(2) { index ->
            val alarmType = index + 1
            val pendingIntent = createPendingIntent(meetId, meetName, alarmType)
            alarmManager.cancel(pendingIntent)
        }
    }
}