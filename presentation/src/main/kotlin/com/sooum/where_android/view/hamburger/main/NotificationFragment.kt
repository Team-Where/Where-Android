package com.sooum.where_android.view.hamburger.main

import android.view.View
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

//        val dummyList = listOf<NotificationItem>()
//
//        val adapter = NotificationRecyclerView(dummyList)
//        recyclerNotification.adapter = adapter
//        recyclerNotification.layoutManager = LinearLayoutManager(requireContext())
//
//        val isEmpty = dummyList.isEmpty()
//        iconWarning.visibility = if (isEmpty) View.VISIBLE else View.GONE
//        textNoNotification.visibility = if (isEmpty) View.VISIBLE else View.GONE
//        recyclerNotification.visibility = if (isEmpty) View.GONE else View.VISIBLE
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