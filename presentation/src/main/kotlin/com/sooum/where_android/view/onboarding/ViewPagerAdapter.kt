package com.sooum.where_android.view.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val fragments : List<Fragment> = listOf(
        OnBoardingFirstFragment(),
        OnBoardingSecondFragment(),
        OnBoardingThirdFragment()
    )
) : FragmentStateAdapter(fragmentActivity)  {
    private val itemCount = Int.MAX_VALUE

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        val realPosition = position % getRealItemCount()
        return fragments[realPosition]
    }

    override fun getItemId(position: Int): Long {
        val realPosition = position % getRealItemCount()
        return fragments[realPosition].hashCode().toLong()
    }

    private fun getRealItemCount(): Int {
        return fragments.size
    }
}