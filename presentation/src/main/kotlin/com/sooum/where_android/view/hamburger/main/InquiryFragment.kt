package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentInquiryBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment

class InquiryFragment : HamburgerBaseFragment<FragmentInquiryBinding>(
    FragmentInquiryBinding ::inflate
) {
    override fun initView() {

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

            //작성 버튼
//            writeButton.setOnClickListener {
//                navHostController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Write) {
//                    launchSingleTop = true
//                }
//            }

        }
    }
}