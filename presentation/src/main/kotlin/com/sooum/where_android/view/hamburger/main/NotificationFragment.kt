package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentNotificationBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment

class NotificationFragment : HamburgerBaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding ::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
//            imageBack.setOnClickListener {
//                navHostController.navigateHome()
//            }
        }
    }
}