package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.domain.model.ActionResult
import com.sooum.where_android.databinding.FragmentEditMyMeetDetailBinding
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel

class EditMyMeetDetailFragment : BottomSheetDialogFragment() {
    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    private lateinit var binding: FragmentEditMyMeetDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMyMeetDetailBinding.inflate(inflater, container, false)

        setupClickListeners()

        return binding.root
    }

    private fun setupClickListeners() = with(binding) {
        imageClose.setOnClickListener { dismiss() }
        iconEditMeetName.setOnClickListener { showBottomSheet(EditMyMeetNameFragment()) }
        iconEditMeetMemo.setOnClickListener { showBottomSheet(EditMyMeetMemoFragment()) }
        btnDeleteMeet.setOnClickListener {
            myMeetDetailViewModel.exitMeet { result ->
                if (result is ActionResult.Success) {
                    requireActivity().finish()
                }
            }
        }

    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(parentFragmentManager, fragment::class.java.simpleName)
    }
}