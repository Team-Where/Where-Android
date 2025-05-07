package com.sooum.where_android.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.sooum.where_android.R

/**
 * notification 채널을 관리하고 알림 발생을 관리하는 manager
 */
class NotificationUtil(
    val context: Context,
    private val channelId: String,
    channelName: String,
    description: String,
    importance: Int
) {
    constructor(
        context: Context,
        channelId: String,
        channelName: String,
        description: String
    ) : this(context, channelId, channelName, description, NotificationManager.IMPORTANCE_DEFAULT)

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
            .setSmallIcon(R.mipmap.ic_launcher).apply(otherSetting)

        return notificationBuilder
    }

    fun makeNotify(
        id: Int = 0,
        otherSetting: NotificationCompat.Builder.() -> Unit
    ) {
        notificationManager.notify(id, makeDefaultBuilder(otherSetting).build())
    }
}