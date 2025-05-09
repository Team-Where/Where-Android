package com.sooum.where_android.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sooum.where_android.databinding.ActivitySplashBinding
import com.sooum.where_android.nextActivity
import com.sooum.where_android.view.getInviteData
import com.sooum.where_android.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        intent.getInviteData()
        setContentView(binding.root)

        lifecycleScope.launch {
            splashViewModel.checkSplash(
                needUpdate = {
                    //TODO 환님 => 업데이트 알럿 출력
                    //알럿으로 더이상 스플래시에서 진행되지 않고 마켓으로 다운로드 유도
                },
                complete = { dest ->
                    nextActivity(dest)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
