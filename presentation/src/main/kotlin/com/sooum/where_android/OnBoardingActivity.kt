package com.sooum.where_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sooum.where_android.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity(){
    private lateinit var binding : ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.container
        viewPager.adapter = ViewPagerAdapter(this)


    }
}