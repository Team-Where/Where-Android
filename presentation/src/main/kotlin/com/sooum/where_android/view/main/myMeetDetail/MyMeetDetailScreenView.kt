package com.sooum.where_android.view.main.myMeetDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.compose.AndroidFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ActivityMyMeetBinding
import com.sooum.where_android.view.common.BaseViewBindingFragment
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyMeetDetailScreenFragment() : BaseViewBindingFragment<ActivityMyMeetBinding>(
    ActivityMyMeetBinding::inflate
) {
    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    override fun initView() {
        loadFragment(MyMeetDetailFragment())
        setupTabLayoutListener()
        viewLifecycleOwner.lifecycleScope.launch {

            //Start loadData
            arguments?.getLong("id")?.let {
                myMeetDetailViewModel.loadData(it)
            }

            //Data init
            myMeetDetailViewModel.meetDetail.collect {
                setData(it)
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
        parentFragmentManager.beginTransaction()
            .replace(R.id.flame, fragment)
            .commit()
    }

    private fun setData(
        meetDetail: MeetDetail?
    ) {
        meetDetail ?: return
        binding.tvTitle.text = meetDetail.title
    }

    fun setBack(
        onClick: () -> Unit
    ) {
        binding.btnBack.setOnClickListener {
            onClick()
        }
    }
}

@Composable
fun MyMeetDetailScreenView(
    id: Long,
    onBack: () -> Unit
) {
    //MyMeetDetailScreenFragment 연결
    AndroidFragment<MyMeetDetailScreenFragment>(
        modifier = Modifier.fillMaxSize(),
        //가져온 id를 전달
        arguments = bundleOf("id" to id)
    ) { fragment ->
        fragment.setBack(onClick = onBack)
    }
}