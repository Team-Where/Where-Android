package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentSettingBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class SettingFragment : HamburgerBaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageClose.setOnClickListener {
                navHostController.navigateHome()
            }

            linearLayout1.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.EditPassword) {
                    launchSingleTop = true
                }
            }

            linearLayout3.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteAccount) {
                    launchSingleTop = true
                }
            }
        }
    }
}