package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditMyMeetMemoBinding
import com.sooum.where_android.databinding.FragmentEditMyMeetNameBinding

class EditMyMeetMemoFragment: BottomSheetDialogFragment() {
    private lateinit var binding : FragmentEditMyMeetMemoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditMyMeetMemoBinding.inflate(inflater, container, false)

        with(binding){
            imageClose.setOnClickListener { dismiss() }
        }

        return binding.root
    }
}