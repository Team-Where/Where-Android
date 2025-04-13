package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.domain.model.ActionResult
import com.sooum.where_android.databinding.FragmentEditMyMeetDetailBinding
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import kotlinx.coroutines.launch

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                myMeetDetailViewModel.meetDetail.collect { detail ->
                    detail?.let {
                        binding.textTitle.text = detail.title
                        binding.textMemo.text = detail.description
                    }
                }
            }
        }

    }

    private fun setupClickListeners() = with(binding) {
        imageClose.setOnClickListener {
            dismiss()
        }

        iconEditMeetName.setOnClickListener {
            myMeetDetailViewModel.meetDetail.value?.let {
                showBottomSheet(
                    EditMyMeetDataFragment.getInstance(
                        type = EditMyMeetDataFragment.TYPE_TITLE,
                        prevData = it.title
                    )
                )
            }
        }
        iconEditMeetMemo.setOnClickListener {
            myMeetDetailViewModel.meetDetail.value?.let {
                showBottomSheet(
                    EditMyMeetDataFragment.getInstance(
                        type = EditMyMeetDataFragment.TYPE_MEMO,
                        prevData = it.description
                    )
                )
            }
        }

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