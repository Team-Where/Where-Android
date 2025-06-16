package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.NoticeResult
import com.sooum.where_android.databinding.FragmentNoticeBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.NoticeItemRecyclerView

class NoticeFragment : HamburgerBaseFragment<FragmentNoticeBinding>(
    FragmentNoticeBinding ::inflate
) {
    override fun initView() {
        val sampleData = listOf(
            NoticeResult(1,"🎉 신규가입 이벤트 당첨자 발표", "2024.12.24", "2024.12.2"),
            NoticeResult(2,"📢 신규 기능 업데이트 안내", "2024.12.01", "2024.12.2"),
            NoticeResult(3,"⚠ 서버 점검 공지", "2024.11.04", "2024.12.2")
        )

        val adapter = NoticeItemRecyclerView(sampleData)
        binding.recyclerNotice.adapter = adapter
        binding.recyclerNotice.layoutManager = LinearLayoutManager(requireContext())
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