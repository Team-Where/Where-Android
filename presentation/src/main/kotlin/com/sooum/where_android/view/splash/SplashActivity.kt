package com.sooum.where_android.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sooum.where_android.databinding.ActivitySplashBinding
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val timeJob = async {
                //3초대기
                delay(3000L)
            }
            val checkLogin = async {
                //로그인 되어있는지 홗인(토큰 확인 등)
                true
            }
            joinAll(timeJob, checkLogin)
            val result = checkLogin.await()
            nextActivity(
                dest = if (result) {
                    MainActivity::class.java
                } else {
                    OnBoardingActivity::class.java
                }
            )

        }
    }

    private fun nextActivity(
        dest: Class<*>
    ) {
        Intent(this@SplashActivity, dest).apply {
            putExtras(intent)
        }.also {
            startActivity(it)
        }
        finish()
    }
}
