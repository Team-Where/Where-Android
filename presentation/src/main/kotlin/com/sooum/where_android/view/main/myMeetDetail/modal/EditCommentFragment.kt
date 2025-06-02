package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditCommentBinding

class EditCommentFragment : BottomSheetDialogFragment() {

    companion object {
        private const val PREV_COMMENT = "prev_comment"

        fun getInstance(
            prevComment: String
        ): EditCommentFragment = EditCommentFragment().apply {
            arguments = bundleOf(
                PREV_COMMENT to prevComment
            )
        }
    }
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
        val prevComment = arguments?.getString(PREV_COMMENT) ?: ""
        with(binding){
            textComment.text = prevComment

            imageClose.setOnClickListener {
                dismiss()
            }

            btnEdit.setOnClickListener {
                showBottomSheet(
                    EditMyMeetDataFragment.newInstance(
                        type =   EditMyMeetDataFragment.TYPE_EDIT_COMMENT,
                        prevData = prevComment
                    )
                )
            }
        }
    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(parentFragmentManager, fragment::class.java.simpleName)
    }

}
