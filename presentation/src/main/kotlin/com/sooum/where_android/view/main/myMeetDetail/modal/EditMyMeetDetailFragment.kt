package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditMyMeetDetailBinding

class EditMyMeetDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentEditMyMeetDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditMyMeetDetailBinding.inflate(inflater, container, false)

        setupClickListeners()

        return binding.root
    }

    private fun setupClickListeners() = with(binding) {
        imageClose.setOnClickListener { dismiss() }
        iconEditMeetName.setOnClickListener { showBottomSheet(EditMyMeetNameFragment()) }
        iconEditMeetMemo.setOnClickListener { showBottomSheet(EditMyMeetMemoFragment()) }
    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(parentFragmentManager, fragment::class.java.simpleName)
    }
}