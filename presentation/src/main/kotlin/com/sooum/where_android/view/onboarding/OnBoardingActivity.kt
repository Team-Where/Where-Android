package com.sooum.where_android.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.where_android.databinding.ActivityOnBoardingBinding
import com.sooum.where_android.nextActivity
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.getInviteData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingBinding

    @Inject lateinit var appManageDataStore: AppManageDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        intent.getInviteData()
        setContentView(binding.root)

        with(binding.container) {
            adapter = ViewPagerAdapter(this@OnBoardingActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    with(binding.imageBack) {
                        visibility = if (position == 0) {
                            View.INVISIBLE
                        } else {
                            View.VISIBLE
                        }
                    }
                    if (position == binding.container.adapter!!.itemCount -1) {
                        binding.skipText.visibility = View.GONE
                        binding.nextBtn.visibility = View.VISIBLE
                    } else {
                        binding.skipText.visibility = View.VISIBLE
                        binding.nextBtn.visibility = View.INVISIBLE
                    }
                }
            })
        }

        with(binding) {
            nextBtn.setOnClickListener {
                goNextActivity()
            }

            skipText.setOnClickListener {
                goNextActivity()
            }

            imageBack.setOnClickListener {
                val prevItem = container.currentItem - 1
                if (prevItem >= 0) {
                    container.currentItem = prevItem
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun goNextActivity() {
        lifecycleScope.launch {
            appManageDataStore.setNotFirstLaunch()
            nextActivity(AuthActivity::class.java)
        }
    }
}
