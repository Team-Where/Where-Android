package com.sooum.where_android.view.hamburger.setting

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentEditPasswordBinding
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment

class EditPasswordFragment : HamburgerBaseFragment<FragmentEditPasswordBinding>(
    FragmentEditPasswordBinding::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageClose.setOnClickListener {
                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                    launchSingleTop = true
                    popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting)
                }
            }
        }
    }
}