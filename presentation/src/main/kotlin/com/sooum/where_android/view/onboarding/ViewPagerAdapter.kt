package com.sooum.where_android.view.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sooum.where_android.R

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val pages = listOf(
        OnBoardingFragment.newInstance(R.string.onboarding_logo1, R.drawable.image_splash_1),
        OnBoardingFragment.newInstance(R.string.onboarding_logo2, R.drawable.image_splash_2),
        OnBoardingFragment.newInstance(R.string.onboarding_logo3, R.drawable.image_splash_3),
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}
