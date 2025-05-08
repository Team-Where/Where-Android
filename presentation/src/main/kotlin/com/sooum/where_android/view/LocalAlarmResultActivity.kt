package com.sooum.where_android.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.sooum.core.notification.alarm.AlarmReceiver
import com.sooum.where_android.WhereApp
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.splash.SplashActivity
import java.io.Serializable

internal const val LOCAL_ALARM_PROVIDER = "localAlarmProvider"


class LocalAlarmResultActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val activity = WhereApp.currentActivity?.get()

        val meetId = intent.getIntExtra(AlarmReceiver.MEET_ID, -1)

        if (meetId >= 0) {
            val intent = when (activity) {
                null -> {
                    Intent(this@LocalAlarmResultActivity, SplashActivity::class.java)
                }

                is MainActivity,
                is MyMeetActivity -> {
                    Intent(this@LocalAlarmResultActivity, MyMeetActivity::class.java)
                }

                else -> {
                    Intent(this@LocalAlarmResultActivity, activity::class.java)
                }
            }
            intent.addLocalAlarmProvider(meetId)
            finish()
            startActivity(intent)
        } else {
            clearActivity()
        }
    }

    private fun clearActivity() {
        val activity = WhereApp.currentActivity?.get()
        finish()
        if (activity != null) {
            val intent = Intent(this@LocalAlarmResultActivity, activity::class.java)
            startActivity(intent)
        }
    }
}

data class LocalAlarmProvider(
    val meetId: Int
) : Serializable

fun Intent.addLocalAlarmProvider(
    meetId: Int
) {
    this.putExtras(
        Bundle().apply {
            putSerializable(LOCAL_ALARM_PROVIDER, LocalAlarmProvider(meetId))
        }
    )
}

fun Intent.getLocalAlarmProvider(): LocalAlarmProvider? {
    val localAlarmProvider = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(LOCAL_ALARM_PROVIDER, LocalAlarmProvider::class.java)
    } else {
        getSerializableExtra(LOCAL_ALARM_PROVIDER) as LocalAlarmProvider?
    }
    return localAlarmProvider
}