package com.sooum.where_android.view.hamburger.setting

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentDeleteAccountCompleteBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment

class DeleteAccountCompleteFragment : HamburgerBaseFragment<FragmentDeleteAccountCompleteBinding>(
    FragmentDeleteAccountCompleteBinding::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
//            nextBtn.setOnClickListener {
//                navHostController.navigate(ScreenRoute.AuthRoute) {
//                    popUpTo(ScreenRoute.HomeRoute) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
//            }
        }
    }
}