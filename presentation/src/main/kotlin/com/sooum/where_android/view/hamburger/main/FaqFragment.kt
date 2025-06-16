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
        val faqList = listOf(
            FaqItem(
                title = FaqConst.FAQ_WHAT_IS_SERVICE,
                content = FaqConst.FAQ_WHAT_IS_SERVICE_STRING
            ),
            FaqItem(
                title = FaqConst.FAQ_CHANGE_NICKNAME,
                content = FaqConst.FAQ_CHANGE_NICKNAME_STRING
            ),
            FaqItem(
                title = FaqConst.FAQ_LOGOUT,
                content = FaqConst.FAQ_LOGOUT_STRING
            ),
            FaqItem(
                title = FaqConst.FAQ_CHANGE_PASSWORD,
                content = FaqConst.FAQ_CHANGE_PASSWORD_STRING
            ),
            FaqItem(
                title = FaqConst.FAQ_MAKE_MEET,
                content = FaqConst.FAQ_MAKE_MEET_STRING
            )
        )

        val faqAdapter = FaqRecyclerView(faqList)
        recyclerFaq.layoutManager = LinearLayoutManager(requireContext())
        recyclerFaq.adapter = faqAdapter
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