package com.sooum.where_android.view.hamburger.main

import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.NotificationItem
import com.sooum.where_android.databinding.FragmentNotificationBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.NotificationRecyclerView

class NotificationFragment : HamburgerBaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding ::inflate
) {
    override fun initView() = with(binding) {
        val dummyList = listOf(
            NotificationItem(
                id = 1,
                imageSrc = "ic_notification",
                title = "친구 추가",
                description = "남봉균팀님이 도꾸님을 친구로 추가했어요.",
                receiveTime = "2025-06-16 09:45:00",
                type = "FRIEND"
            ),
            NotificationItem(
                id = 2,
                imageSrc = "ic_notification",
                title = "공지",
                description = "[이벤트] 신규가입 이벤트 당첨자가 발표되었어요.",
                receiveTime = "2025-06-15 22:30:00",
                type = "NOTICE"
            ),
            NotificationItem(
                id = 3,
                imageSrc = "ic_notification",
                title = "모임 초대",
                description = "도꾸님이 남봉균팀님의 모임에 초대되었어요.",
                receiveTime = "2025-06-14 19:00:00",
                type = "INVITE"
            )
        )

        val adapter = NotificationRecyclerView(dummyList)
        recyclerNotification.adapter = adapter
        recyclerNotification.layoutManager = LinearLayoutManager(requireContext())
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