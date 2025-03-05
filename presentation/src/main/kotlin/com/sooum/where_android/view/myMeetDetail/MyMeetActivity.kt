package com.sooum.where_android.view.myMeetDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ActivityMyMeetBinding

class MyMeetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyMeetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMeetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(MyMeetDetailFragment())
        setupTabLayoutListener()
    }

    private fun setupTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> MyMeetDetailFragment()
                    1 -> MyMeetPlaceFragment()
                    else -> return
                }
                loadFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flame, fragment)
            .commit()
    }

}