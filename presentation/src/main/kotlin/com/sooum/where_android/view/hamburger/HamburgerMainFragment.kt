package com.sooum.where_android.view.hamburger

import android.os.Bundle
import android.view.View
import com.sooum.where_android.databinding.FragmentHamburgerMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HamburgerMainFragment : HamburgerBaseFragment<FragmentHamburgerMainBinding>(
    FragmentHamburgerMainBinding::inflate
) {
    override fun initView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}