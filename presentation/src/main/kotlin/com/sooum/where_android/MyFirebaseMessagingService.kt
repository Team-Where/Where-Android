package com.sooum.where_android

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.sooum.where_android.di.FCMTool
import com.sooum.where_android.util.NotificationUtil
import com.sooum.where_android.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    @FCMTool
    lateinit var fcmNotificationUtil: NotificationUtil

    // 메시지를 수신할 때 호출 된다.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            //데이터 항목이 있는 경우
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
            //데이터 항목이 없는 경우
            remoteMessage.notification?.let {
                val title = it.title.orEmpty()
                val body = it.body.orEmpty()

                if (title.isNotBlank() || body.isNotBlank()) {
                    sendNotification(title, body)
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //TODO 신규 토큰 갱신시 로그인 되어있다면 서버로 갱신 요청
    }

    /**
     * 브로드케스트로 데이터를 보내는 함수
     */
    private fun sendBroadcastWithData(code: String, data: String) {
        val intent = Intent(FCM_DATA_RECEIVED).apply {
            putExtra(FCM_EXTRA_CODE, code)
            putExtra(FCM_EXTRA_DATA, data)
        }
        sendBroadcast(intent)
    }

    // 수신 된 FCM 메시지를 포함하는 간단한 알림을 만들고 표시한다.
    @SuppressLint("ServiceCast")
    private fun sendNotification(
        title: String,
        body: String
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        fcmNotificationUtil.makeNotify(
            0,
        ) {
            setContentTitle(title)
            setContentText(body)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"

        const val FCM_DATA_RECEIVED = "FCM_DATA_RECEIVED"
        const val FCM_EXTRA_CODE = "code"
        const val FCM_EXTRA_DATA = "data"
    }
}