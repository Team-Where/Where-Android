package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentFaqBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class FaqFragment : HamburgerBaseFragment<FragmentFaqBinding>(
    FragmentFaqBinding::inflate
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
        }
    }
}