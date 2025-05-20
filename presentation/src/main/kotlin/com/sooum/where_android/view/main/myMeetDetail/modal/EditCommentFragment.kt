package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditCommentBinding
import com.sooum.where_android.databinding.FragmentEditMyMeetDataBinding

class EditCommentFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditCommentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnEdit.setOnClickListener {
                showBottomSheet(
                    EditMyMeetDataFragment.newInstance(
                        type =   EditMyMeetDataFragment.TYPE_EDIT_COMMENT,
                        prevData = "ㅋㅋㅋㅋ"
                    )
                )
            }
        }
    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(parentFragmentManager, fragment::class.java.simpleName)
    }

}
