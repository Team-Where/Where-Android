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
            NoticeResult(1,"🎉 신규가입 이벤트 당첨자 발표", "2024.12.24", "축하드립니다!\n남XX\n이XX\n김XX"),
            NoticeResult(2,"📢 신규 기능 업데이트 안내", "2024.12.01", "신규 기능이 추가되었습니다. 자세한 내용은 앱에서 확인해주세요."),
            NoticeResult(3,"⚠ 서버 점검 공지", "2024.11.04", "서버 점검으로 인해 일시적으로 서비스가 중단됩니다.")
        )

//        val adapter = NoticeItemRecyclerView(sampleData)
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