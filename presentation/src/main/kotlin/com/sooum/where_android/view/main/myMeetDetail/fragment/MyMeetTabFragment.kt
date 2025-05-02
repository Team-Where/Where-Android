package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.sooum.where_android.databinding.FragmentMyMeetTabBinding
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.EditMyMeetDetailFragment
import kotlinx.coroutines.launch

/**
 * 네비게이션 사용을 위해 Tab 화면을 분리
 * @see[R.navigation.nav_graph]
 */
class MyMeetTabFragment : MyMeetBaseFragment<FragmentMyMeetTabBinding>(
    FragmentMyMeetTabBinding::inflate
) {

    override fun initView() {
        setupTabLayoutListener()

        with(binding) {
            btnBack.setOnClickListener {
                activity?.finish()
            }

            imageEdit.setOnClickListener {
                val bottomSheet = EditMyMeetDetailFragment()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        }
        lifecycleScope.launch {
            myMeetDetailViewModel.meetDetail.collect { meetDetail ->
                binding.tvTitle.text = meetDetail?.title
                binding.imageEdit.isEnabled = meetDetail?.finished != true
            }
        }
    }
    private fun setupTabLayoutListener() {
        with(binding.tabLayout) {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    loadFragment(tab.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            myMeetDetailTabViewModel.selectedTabPosition.let { position ->
                if (position == 1) {
                    val tab = getTabAt(position)
                    selectTab(tab)
                } else {
                    loadFragment(0)
                }
            }
        }
    }

    @SuppressLint("CommitTransaction")
    private fun loadFragment(
        position: Int
    ) {
        val fragment = when (position) {
            0 -> MyMeetDetailFragment()
            1 -> MyMeetPlaceFragment()
            else -> return
        }
        myMeetDetailTabViewModel.selectedTabPosition = position
        parentFragmentManager.beginTransaction()
            .replace(binding.flame.id, fragment)
            .commit()
    }

}