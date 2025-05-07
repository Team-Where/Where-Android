package com.sooum.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

/**
 * notification 채널을 관리하고 알림 발생을 관리하는 manager
 */
class NotificationUtil(
    val context: Context,
    private val channelId: String,
    channelName: String,
    description: String = "",
    importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
    private val config: NotificationConfig,
) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        val channel = NotificationChannel(
            /* id = */ channelId,
            /* name = */ channelName,
            /* importance = */ importance,
        )
        channel.description = description
        notificationManager.createNotificationChannel(channel)
    }


    private fun makeDefaultBuilder(
        otherSetting: NotificationCompat.Builder.() -> Unit
    ): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(config.iconResId)
            .apply(otherSetting)

        return notificationBuilder
    }

    private var notificationId = 0

    fun makeNotify(
        id: Int? = null,
        otherSetting: NotificationCompat.Builder.() -> Unit
    ) {
        val newId = id ?: notificationId++
        notificationManager.notify(newId, makeDefaultBuilder(otherSetting).build())
    }
}