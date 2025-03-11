package com.sooum.where_android.view.main.myMeetDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ActivityMyMeetBinding
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyMeetActivity : AppCompatActivity() {

    companion object {
        const val MEET_ID = "meetId"
    }
    private lateinit var binding: ActivityMyMeetBinding

    private val myMeetDetailViewModel : MyMeetDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMeetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(MyMeetDetailFragment())
        setupTabLayoutListener()

        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
        }
        intent.getLongExtra(MEET_ID,0L).let { id ->
            myMeetDetailViewModel.loadData(id)
        }

        lifecycleScope.launch {
            myMeetDetailViewModel.meetDetail.collect { meetDetail ->
                binding.tvTitle.text = meetDetail?.title
            }
        }
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

    @SuppressLint("CommitTransaction")
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flame, fragment)
            .commit()
    }

}