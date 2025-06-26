package com.sooum.where_android.view.hamburger.main

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.where_android.databinding.FragmentNotificationBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.inquiry.InquiryWaitingAnswerFragment
import com.sooum.where_android.view.hamburger.navigateHome
import com.sooum.where_android.view.hamburger.main.adapter.NotificationRecyclerView
import kotlinx.coroutines.launch

class NotificationFragment : HamburgerBaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding ::inflate
) {

    private lateinit var notificationAdapter: NotificationRecyclerView

    override fun initView() {
        with(binding){
            notificationAdapter = NotificationRecyclerView(emptyList())
            recyclerNotification.apply {
                adapter = notificationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }


            lifecycleScope.launch {
                notificationViewModel.notificationList.collect { list ->
                    val isEmpty = list.isEmpty()
                    iconWarning.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    textNoNotification.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    recyclerNotification.visibility = if (isEmpty) View.GONE else View.VISIBLE

                    notificationAdapter = NotificationRecyclerView(list)
                    recyclerNotification.apply {
                        adapter = notificationAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
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