package com.sooum.where_android.view.hamburger.main.inquiry

import android.view.View
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.InquiryResult
import com.sooum.where_android.databinding.FragmentInquiryBinding
import com.sooum.where_android.databinding.FragmentInquiryWaitingAnswerBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.InquiryRecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class InquiryWaitingAnswerFragment : HamburgerBaseFragment<FragmentInquiryWaitingAnswerBinding>(
    FragmentInquiryWaitingAnswerBinding ::inflate
) {
    override fun initView() = with(binding) {
//        val dummyList = getDummyData().filter { !it.answered }
//
//        val adapter = InquiryRecyclerView(dummyList)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter

//        val isEmpty = dummyList.isEmpty()
//        iconWarning.visibility = if (isEmpty) View.VISIBLE else View.GONE
//        textNoNotification.visibility = if (isEmpty) View.VISIBLE else View.GONE
//        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

}