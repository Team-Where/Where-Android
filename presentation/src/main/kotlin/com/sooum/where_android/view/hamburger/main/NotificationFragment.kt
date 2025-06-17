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

//        val adapter = NotificationRecyclerView(dummyList)
//        recyclerNotification.adapter = adapter
//        recyclerNotification.layoutManager = LinearLayoutManager(requireContext())
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