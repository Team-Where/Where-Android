package com.sooum.where_android.view.hamburger.main.inquiry

import com.sooum.where_android.databinding.FragmentInquiryBinding
import com.sooum.where_android.databinding.FragmentInquiryWriteBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.navigateHome

class InquiryWriteFragment : HamburgerBaseFragment<FragmentInquiryWriteBinding>(
    FragmentInquiryWriteBinding ::inflate
) {
    override fun initView() = with((binding)) {

            imageBack.setOnClickListener {
                navHostController.navigateHome()
            }
    }
}