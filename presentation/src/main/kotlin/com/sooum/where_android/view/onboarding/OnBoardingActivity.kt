package com.sooum.where_android.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sooum.where_android.databinding.ActivityOnBoardingBinding
import com.sooum.where_android.view.auth.AuthActivity

class OnBoardingActivity : AppCompatActivity(){
    private lateinit var binding : ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.container.adapter = ViewPagerAdapter(this)

        binding.container.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == binding.container.adapter!!.itemCount -1) {
                    binding.skipText.visibility = View.GONE
                    binding.nextBtn.visibility = View.VISIBLE
                } else {
                    binding.skipText.visibility = View.VISIBLE
                    binding.nextBtn.visibility = View.INVISIBLE
                }
            }
        })

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        binding.skipText.setOnClickListener {
            val nextItem = binding.container.currentItem + 1
            if (nextItem < binding.container.adapter!!.itemCount) {
                binding.container.currentItem = nextItem
            }
        }

        binding.imageBack.setOnClickListener {
            val prevItem = binding.container.currentItem - 1
            if (prevItem >= 0) {
                binding.container.currentItem = prevItem
            }
        }
    }
}
