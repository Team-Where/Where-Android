package com.sooum.where_android.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sooum.where_android.view.MainActivity
import com.sooum.where_android.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity(){
    private lateinit var binding : ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.container.adapter = ViewPagerAdapter(this)

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}