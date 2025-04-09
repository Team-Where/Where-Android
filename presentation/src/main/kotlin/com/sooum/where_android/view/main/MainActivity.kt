package com.sooum.where_android.view.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.firebase.messaging.FirebaseMessaging
import com.sooum.where_android.databinding.ActivityMainBinding
import com.sooum.where_android.util.KaKaoShareUtil
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

        // 로그 출력
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("MainActivity", "FCM Token: $token")
                }
            }



        with(binding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
            setContent {
                CompositionLocalProvider(
                    LocalActivity provides this@MainActivity
                ) {
                    MainScreenView(
                        modifier = Modifier.fillMaxSize()
                    )
                    Button(
                        onClick = {

                        }
                    ) {
                        Text("123")
                    }
                }
            }
        }

        setContentView(binding.root)
    }
}