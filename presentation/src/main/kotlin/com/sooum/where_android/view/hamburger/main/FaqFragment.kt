package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.core.FaqConst
import com.sooum.domain.model.FaqItem
import com.sooum.domain.model.NoticeResult
import com.sooum.where_android.databinding.FragmentFaqBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.FaqRecyclerView
import com.sooum.where_android.view.hamburger.main.adapter.NoticeRecyclerView
import com.sooum.where_android.view.hamburger.navigateHome

class FaqFragment : HamburgerBaseFragment<FragmentFaqBinding>(
    FragmentFaqBinding::inflate
) {
    override fun initView() = with(binding) {

//        val faqAdapter = FaqRecyclerView(faqList)
//        recyclerFaq.layoutManager = LinearLayoutManager(requireContext())
//        recyclerFaq.adapter = faqAdapter
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