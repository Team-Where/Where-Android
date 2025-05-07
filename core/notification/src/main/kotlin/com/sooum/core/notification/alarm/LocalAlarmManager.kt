package com.sooum.core.notification.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.sooum.core.alarm.AlarmMaker
import java.time.LocalDateTime
import java.time.ZoneId

class LocalAlarmManager(
    private val context: Context
) : AlarmMaker {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    private fun createPendingIntent(meetId: Int, alarmType: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "ALARM_${meetId}_$alarmType"
            putExtra(AlarmReceiver.MEET_ID, meetId)
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
        standardData: LocalDateTime
    ) {
        val alarmTimes = listOf(
            standardData.minusHours(24),
            standardData.minusHours(1)
        )

        alarmTimes.forEachIndexed { index, time ->
            Log.d("JWH", "\t[$meetId] add type $index")
            Log.d("JWH", "\t[$meetId] will time = $time")
            val triggerMillis = time.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
            Log.d("JWH", "\t[$meetId] will time = $triggerMillis")
            val nowMillis = System.currentTimeMillis()

            val alarmType = index + 1
            val pendingIntent = createPendingIntent(meetId, alarmType)
            alarmManager.cancel(pendingIntent)

            if (triggerMillis <= nowMillis) {
                // 이미 지난 알람은 무시
                Log.d("JWH", "\t\t[$meetId] pass by time over")
                return@forEachIndexed
            }
            Log.d("JWH", "\t\t[$meetId] add time to $triggerMillis")

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

    override fun cancelAlarm(meetId: Int) {
        repeat(2) { index ->
            val alarmType = index + 1
            val pendingIntent = createPendingIntent(meetId, alarmType)
            alarmManager.cancel(pendingIntent)
            Log.d("JWH", "[$meetId] cancel type $index")
        }
    }
}