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
import com.sooum.domain.model.SimpleMeet
import com.sooum.domain.usecase.meet.invite.GetMeetInviteLinkUseCase
import com.sooum.where_android.WhereApp
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
            val activity = WhereApp.currentActivity
            val name = intent.data?.getQueryParameter("user")
            val code = intent.data?.pathSegments?.lastOrNull()

            if (name == null || code == null || code.length != 10) {
                finish()
            }

            lifecycleScope.launch {
                val result = getMeetInviteLinkUseCase(code!!).first()
                if (result is ApiResult.Success) {
                    val simpleMeet = result.data
                    val intent = if (activity == null) {
                        Intent(this@SchemeResultActivity, SplashActivity::class.java)
                    } else {
                        Intent(this@SchemeResultActivity, activity::class.java)
                    }
                    intent.apply {
                        addInviteScheme(simpleMeet, name!!)
                    }
                    finish()
                    startActivity(intent)
                }
            }
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
) :Boolean {
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
        return  true
    }
    return false
}