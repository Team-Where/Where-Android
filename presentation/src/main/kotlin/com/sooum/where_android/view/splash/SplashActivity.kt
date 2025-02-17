package com.sooum.where_android.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sooum.where_android.databinding.ActivitySplashBinding
import android.os.Handler
import com.sooum.where_android.view.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashActivity,OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3초

    }
}