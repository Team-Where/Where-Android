package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.NoticeResult
import com.sooum.where_android.databinding.FragmentNoticeBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.NoticeRecyclerView

class NoticeFragment : HamburgerBaseFragment<FragmentNoticeBinding>(
    FragmentNoticeBinding ::inflate
) {
    override fun initView() {

//        val adapter = NoticeRecyclerView(sampleData)
//        binding.recyclerNotice.adapter = adapter
//        binding.recyclerNotice.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setNavigation(
        navHostController: NavHostController
    ) {
        super.setNavigation(navHostController)
        with(binding) {
//            imageBack.setOnClickListener {
//                navHostController.navigateHome()
//            }
        }
    }
}