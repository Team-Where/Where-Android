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


    @Inject
    @LocalTool
    lateinit var localNotificationUtil: NotificationUtil

    override fun onReceive(context: Context, intent: Intent) {
        // 예시: 알림 표시
        localNotificationUtil.makeNotify {
            setContentTitle("Test1")
            setContentText("Test Test")
        }
    }
}