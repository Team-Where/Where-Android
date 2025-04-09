package com.sooum.where_android.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.ShareResult
import com.sooum.domain.model.SimpleMeet
import com.sooum.domain.model.onSuccess
import com.sooum.domain.usecase.meet.invite.GetMeetInviteLinkUseCase
import com.sooum.where_android.WhereApp
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.widget.CoverImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


/**
 * 스킵 데이터 터리를 위해
 */
@AndroidEntryPoint
class SchemeResultActivity : AppCompatActivity() {

    @Inject
    lateinit var getMeetInviteLinkUseCase: GetMeetInviteLinkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = WhereApp.currentActivity
        Log.d("JWH", activity.toString())

        Log.d("JWH", intent.toString())
        intent?.let {
            Log.d("JWH", it.action.toString())
            Log.d("JWH", it.data.toString())
            Log.d("JWH", it.data?.pathSegments.toString())
            Log.d("JWH", it.extras.toString())
        }
        setContent {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                var simpleMeet: SimpleMeet? by remember {
                    mutableStateOf(null)
                }
                LaunchedEffect(true) {
                    intent.data?.pathSegments?.lastOrNull()?.let { code ->
                        if (code.length == 10) {
                            val result = getMeetInviteLinkUseCase(code).first()
                            if (result is ApiResult.Success) {
                                simpleMeet = result.data
                            }
                            Log.d("JWH", result.toString())
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .height(386.dp)
                        .align(Alignment.Center)
                        .border(1.dp, Gray600, RoundedCornerShape(16.dp))
                        .padding(horizontal = 91.dp)
                        .padding(vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    simpleMeet?.let { data ->
                        data.CoverImage(120.dp, 16.dp)

                        Text(data.title)

                        Text("${data.date} + ${data.time}")
                    }
                }
            }
        }
    }
}