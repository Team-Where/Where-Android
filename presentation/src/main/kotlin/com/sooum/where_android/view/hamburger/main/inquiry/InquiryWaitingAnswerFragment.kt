package com.sooum.where_android.view.hamburger.main.inquiry

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.where_android.databinding.FragmentInquiryWaitingAnswerBinding
import com.sooum.where_android.view.hamburger.HamburgerBaseFragment
import com.sooum.where_android.view.hamburger.main.adapter.InquiryRecyclerView
import kotlinx.coroutines.launch

class InquiryWaitingAnswerFragment : HamburgerBaseFragment<FragmentInquiryWaitingAnswerBinding>(
    FragmentInquiryWaitingAnswerBinding ::inflate
) {
    override fun initView() {

       lifecycleScope.launch {
          repeatOnLifecycle(Lifecycle.State.STARTED) {
              inquiryViewModel.unAnsweredList.collect {
                    val adapter = InquiryRecyclerView(it)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

                    val isEmpty = it.isEmpty()
                    binding.iconWarning.visibility = if (isEmpty) android.view.View.VISIBLE else android.view.View.GONE
                    binding.textNoNotification.visibility = if (isEmpty) android.view.View.VISIBLE else android.view.View.GONE
                    binding.recyclerView.visibility = if (isEmpty) android.view.View.GONE else android.view.View.VISIBLE
              }
          }
        }
    }

}