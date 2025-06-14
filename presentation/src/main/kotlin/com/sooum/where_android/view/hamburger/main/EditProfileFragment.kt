package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import com.sooum.where_android.databinding.FragmentEditProfileBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class EditProfileFragment : HamburgerBaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate
) {
    override fun initView() {

    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
            imageBack.setOnClickListener {
                navHostController.navigateHome()
            }
        }
    }
}