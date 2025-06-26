package com.sooum.where_android.view.hamburger.main

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Lifecycle
import com.sooum.where_android.view.hamburger.main.inquiry.InquiryWaitingAnswerFragment
import com.sooum.where_android.view.hamburger.navigateHome
import com.sooum.where_android.databinding.FragmentNoticeBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.NoticeRecyclerView
import kotlinx.coroutines.launch

class NoticeFragment : HamburgerBaseFragment<FragmentNoticeBinding>(
    FragmentNoticeBinding ::inflate
) {

    override fun initView() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noticeViewModel.noticeList.collect {

                    val adapter = NoticeRecyclerView(it)
                    binding.recyclerNotice.adapter = adapter
                    binding.recyclerNotice.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
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