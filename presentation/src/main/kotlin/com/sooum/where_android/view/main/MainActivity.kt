package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.sooum.where_android.databinding.ActivityMainBinding
import com.sooum.where_android.view.checkInviteData
import com.sooum.where_android.view.getLocalAlarmProvider
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import dagger.hilt.android.AndroidEntryPoint


val LocalActivity = compositionLocalOf<MainActivity> {
    error("CompositionLocal LocalActivity not present")
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        intent?.checkInviteData(this@MainActivity)

        with(binding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
            setContent {
                CompositionLocalProvider(
                    LocalActivity provides this@MainActivity
                ) {
                    MainScreenView(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        setContentView(binding.root)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.checkInviteData(this@MainActivity)
        intent?.getLocalAlarmProvider()?.let {
            val id = it.meetId
            startActivity(
                Intent(this, MyMeetActivity::class.java).apply {
                    putExtras(Bundle().apply {
                        putInt(MyMeetActivity.MEET_ID, id)
                    })
                }
            )
        }
    }
}