package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditMyMeetDataBinding
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailCommentViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailViewModel


class EditMyMeetDataFragment() : BottomSheetDialogFragment() {
    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()
    private val myMeetDetailCommentViewModel: MyMeetDetailCommentViewModel by activityViewModels()
    private lateinit var binding: FragmentEditMyMeetDataBinding

    companion object {
        private const val TYPE_TITLE = 1
        private const val TYPE_MEMO = 2
        private const val TYPE_ADD_COMMENT = 3
        private const val TYPE_EDIT_COMMENT = 4

        private const val EDIT_TYPE = "myMeetEditType"
        private const val PREV_DATA_KEY = "myMeetPrevData"
        private const val COMMENT_ID_DATA_KEY = "commentIdData"


        fun titleEdit(
            prevData: String,
        ): EditMyMeetDataFragment {
            return EditMyMeetDataFragment().apply {
                arguments = bundleOf(
                    EDIT_TYPE to TYPE_TITLE,
                    PREV_DATA_KEY to prevData,
                )
            }
        }

        fun memoEdit(
            prevData: String,
        ): EditMyMeetDataFragment {
            return EditMyMeetDataFragment().apply {
                arguments = bundleOf(
                    EDIT_TYPE to TYPE_MEMO,
                    PREV_DATA_KEY to prevData,
                )
            }
        }

        fun commentAdd(): EditMyMeetDataFragment {
            return EditMyMeetDataFragment().apply {
                arguments = bundleOf(
                    EDIT_TYPE to TYPE_ADD_COMMENT,
                )
            }
        }

        fun commentEdit(
            commentId: Int,
            prevData: String,
        ): EditMyMeetDataFragment {
            return EditMyMeetDataFragment().apply {
                arguments = bundleOf(
                    EDIT_TYPE to TYPE_EDIT_COMMENT,
                    COMMENT_ID_DATA_KEY to commentId,
                    PREV_DATA_KEY to prevData,
                )
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMyMeetDataBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = requireArguments().getInt(EDIT_TYPE)
        val prevTitle = requireArguments().getString(PREV_DATA_KEY)

        with(binding) {

            when (type) {
                TYPE_TITLE -> {
                    textMeetName.text = "모임 이름"
                    editMyMeetMemo.hint = "모임 이름을 입력해주세요."
                    editMyMeetMemo.setFilters(arrayOf<InputFilter>(LengthFilter(16)))
                }

                TYPE_MEMO -> {
                    textMeetName.text = "메모"
                    editMyMeetMemo.hint = "모임에 대한 간단한 소개를 입력해주세요."
                    editMyMeetMemo.setFilters(arrayOf<InputFilter>(LengthFilter(30)))
                }

                TYPE_ADD_COMMENT -> {
                    textMeetName.text = "코멘트 남기기"
                    editMyMeetMemo.hint = "친구들이 볼 수 있도록 코멘트를 달아보세요.(0/50)"
                    editMyMeetMemo.setFilters(arrayOf<InputFilter>(LengthFilter(50)))
                }

                TYPE_EDIT_COMMENT -> {
                    textMeetName.text = "코멘트 수정"
                    editMyMeetMemo.hint = "친구들이 볼 수 있도록 코멘트를 달아보세요.(0/50)"
                    editMyMeetMemo.setFilters(arrayOf<InputFilter>(LengthFilter(50)))
                }
            }

            editMyMeetMemo.setText(prevTitle)

            imageClose.setOnClickListener {
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setOnClickListener {
                loadingAlertProvider.startLoading()

                val successAction = {
                    loadingAlertProvider.endLoading {
                        dismiss()
                    }
                }
                val failAction = { msg: String ->
                    loadingAlertProvider.endLoadingWithMessage(msg)
                }
                when (type) {
                    TYPE_TITLE -> {
                        myMeetDetailViewModel.updateTitle(
                            newTitle = getEditText(),
                            onSuccess = successAction,
                            onFail = failAction
                        )
                    }

                    TYPE_MEMO -> {
                        myMeetDetailViewModel.updateDescription(
                            newDescription = getEditText(),
                            onSuccess = successAction,
                            onFail = failAction
                        )
                    }

                    TYPE_ADD_COMMENT -> {
                        myMeetDetailCommentViewModel.addComment(
                            comment = getEditText(),
                            onSuccess = successAction,
                            onFail = failAction
                        )
                    }

                    TYPE_EDIT_COMMENT -> {
                        val id = requireArguments().getInt(COMMENT_ID_DATA_KEY)
                        myMeetDetailCommentViewModel.editComment(
                            commentId = id,
                            comment = getEditText(),
                            onSuccess = successAction,
                            onFail = failAction
                        )
                    }
                }

            }
        }
    }

    private fun getEditText(): String {
        return binding.editMyMeetMemo.text.toString()
    }

    private val loadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }
}