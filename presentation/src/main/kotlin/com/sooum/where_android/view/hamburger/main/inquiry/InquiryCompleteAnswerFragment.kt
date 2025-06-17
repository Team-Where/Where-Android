package com.sooum.where_android.view.hamburger.main.inquiry

import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.InquiryResult
import com.sooum.where_android.databinding.FragmentInquiryCompleteAnswerBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.InquiryRecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class InquiryCompleteAnswerFragment : HamburgerBaseFragment<FragmentInquiryCompleteAnswerBinding>(
    FragmentInquiryCompleteAnswerBinding ::inflate
) {
    override fun initView() = with(binding) {
//        val dummyData = getDummyData().filter { it.answered }
//
//        val adapter = InquiryRecyclerView(dummyData)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter
    }


}