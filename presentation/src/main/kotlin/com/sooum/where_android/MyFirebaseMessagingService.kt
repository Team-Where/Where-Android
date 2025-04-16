package com.sooum.where_android

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.sooum.where_android.view.main.MainActivity

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // 메시지를 수신할 때 호출 된다.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            val code = remoteMessage.data["code"].orEmpty()
            val title = remoteMessage.notification?.title.orEmpty()
            val body = remoteMessage.notification?.body.orEmpty()

            if (title.isNotBlank() || body.isNotBlank()) {
                sendNotification(title, body)
            }

            if (code.isNotBlank()) {
                val fcmData = Gson().toJson(remoteMessage.data)
                sendBroadcastWithData(code, fcmData)
            }

        } else {
            remoteMessage.notification?.let {
                val title = it.title.orEmpty()
                val body = it.body.orEmpty()

                if (title.isNotBlank() || body.isNotBlank()) {
                    sendNotification(title, body)
                }
            }
        }
    }

    /**
     * 브로드케스트로 데이터를 보내는 함수
     */
    private fun sendBroadcastWithData(code: String, data: String) {
        val intent = Intent("FCM_DATA_RECEIVED").apply {
            putExtra("code", code)
            putExtra("data", data)
        }
        sendBroadcast(intent)
    }

    // 수신 된 FCM 메시지를 포함하는 간단한 알림을 만들고 표시한다.
    @SuppressLint("ServiceCast")
    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.image_profile_default_cover)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 이상에서 알림을 제공하려면 앱의 알림 채널을 시스템에 등록해야 한다.
        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}