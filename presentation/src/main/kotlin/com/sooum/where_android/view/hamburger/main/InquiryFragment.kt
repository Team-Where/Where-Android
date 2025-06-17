package com.sooum.where_android.view.hamburger.main

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
import com.google.android.material.tabs.TabLayout
import com.sooum.where_android.databinding.FragmentInquiryBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.inquiry.InquiryCompleteAnswerFragment
import com.sooum.where_android.view.hamburger.main.inquiry.InquiryWaitingAnswerFragment
import com.sooum.where_android.view.main.myMeetDetail.fragment.MyMeetDetailFragment
import com.sooum.where_android.view.main.myMeetDetail.fragment.MyMeetPlaceFragment

class InquiryFragment : HamburgerBaseFragment<FragmentInquiryBinding>(
    FragmentInquiryBinding ::inflate
) {
    override fun initView() {
        setupTabLayoutListener()
    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            //Back버튼
//            imageBack.setOnClickListener {
//                navHostController.navigateHome()
//            }

            writeBtn.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Write) {
                    launchSingleTop = true
                }
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

            inquiryViewModel.selectedTabPosition.let { position ->
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
            0 -> InquiryWaitingAnswerFragment()
            1 -> InquiryCompleteAnswerFragment()
            else -> return
        }
        inquiryViewModel.selectedTabPosition = position
        parentFragmentManager.beginTransaction()
            .replace(binding.flame.id, fragment)
            .commit()
    }

}