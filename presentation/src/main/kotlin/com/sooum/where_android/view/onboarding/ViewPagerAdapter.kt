package com.sooum.where_android.view.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val pages = listOf(
        OnBoardingContentFragment.step1Instance(),
        OnBoardingContentFragment.step2Instance(),
        OnBoardingContentFragment.step3Instance(),
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}
