package com.sooum.where_android.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.SimpleMeet
import com.sooum.domain.usecase.meet.invite.GetMeetInviteLinkUseCase
import com.sooum.where_android.WhereApp
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.invite.InviteBySchemeView
import com.sooum.where_android.view.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

internal const val INVITE_DATA = "inviteData"

/**
 * 스킵 데이터 터리를 위해
 */
@AndroidEntryPoint
class SchemeResultActivity : AppCompatActivity() {

    @Inject
    lateinit var getMeetInviteLinkUseCase: GetMeetInviteLinkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inviteData = intent.getInviteData()
        if (inviteData != null) {
            setContent {
                InviteBySchemeView(
                    inviteData = inviteData,
                    onBack = {
                        finish()
                    },
                    onClick = {

                    }
                )
            }
        } else {
            window.setBackgroundDrawableResource(android.R.color.transparent)
            val activity = WhereApp.currentActivity?.get()
            val name = intent.data?.getQueryParameter("name")
            val code = intent.data?.pathSegments?.lastOrNull()
            Log.d("JWH", intent.data.toString())

            if (name == null || code == null || code.length != 10) {
                clearActivity()
            } else {
                lifecycleScope.launch {
                    val result = getMeetInviteLinkUseCase(code).first()
                    Log.d("JWH", result.toString())
                    when (result) {

                        is ApiResult.Success -> {
                            val simpleMeet = result.data

                            val intent = if (activity == null) {
                                Intent(this@SchemeResultActivity, SplashActivity::class.java)
                            } else {
                                Intent(this@SchemeResultActivity, activity::class.java)
                            }
                            intent.apply {
                                addInviteScheme(simpleMeet, name)
                            }
                            finish()
                            startActivity(intent)
                        }

                        is ApiResult.Fail.Error -> {
                            showSimpleToast(result.message ?: "error")
                            clearActivity()
                        }

                        is ApiResult.Fail.Exception -> {
                            showSimpleToast(result.e.localizedMessage ?: "error")
                            clearActivity()
                        }

                        else -> {
                            showSimpleToast("잘못된 접근 입니다.")
                            clearActivity()
                        }
                    }
                }
            }
        }
    }

    private fun clearActivity() {
        val activity = WhereApp.currentActivity?.get()
        finish()
        if (activity != null) {
            val intent = Intent(this@SchemeResultActivity, activity::class.java)
            startActivity(intent)
        }
    }
}


data class InviteData(
    val id: Int,
    val title: String,
    val image: String?,
    val date: String?,
    val time: String?,
    val name: String
) : Serializable {

    constructor(simpleMeet: SimpleMeet, name: String) : this(
        simpleMeet.id,
        simpleMeet.title,
        simpleMeet.image,
        simpleMeet.date,
        simpleMeet.time,
        name
    )

    val schedule: Schedule?
        get() = if (date != null && time != null) {
            Schedule(id, date, time)
        } else {
            null
        }
}

fun Intent.addInviteScheme(
    simpleMeet: SimpleMeet,
    name: String
) {
    this.putExtras(
        Bundle().apply {
            putSerializable(INVITE_DATA, InviteData(simpleMeet, name))
        }
    )
}

fun Intent.getInviteData(): InviteData? {
    val inviteData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(INVITE_DATA, InviteData::class.java)
    } else {
        getSerializableExtra(INVITE_DATA) as InviteData?
    }
    return inviteData
}

fun Intent.checkInviteData(
    context: Context
): Boolean {
    val inviteData = this.getInviteData()
    inviteData?.let {
        context.startActivity(
            Intent(context, SchemeResultActivity::class.java).apply {
                putExtras(
                    Bundle().apply {
                        putSerializable(INVITE_DATA, inviteData)
                    }
                )
            }
        )
        return true
    }
    return false
}