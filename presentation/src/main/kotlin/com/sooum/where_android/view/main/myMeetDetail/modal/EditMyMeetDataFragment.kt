package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditMyMeetDataBinding
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel

class EditMyMeetDataFragment : BottomSheetDialogFragment() {
    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentEditMyMeetDataBinding

    companion object {
        const val TYPE_TITLE = 1
        const val TYPE_MEMO = 2

        private const val EDIT_TYPE = "myMeetEditType"
        private const val PREV_DATA_KEY = "myMeetPrevData"

        fun getInstance(
            @IntRange(from = 1, to = 2) type: Int,
            prevData: String
        ): EditMyMeetDataFragment {
            return EditMyMeetDataFragment().apply {
                arguments = bundleOf(
                    EDIT_TYPE to type,
                    PREV_DATA_KEY to prevData
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
            if (type == TYPE_TITLE) {
                textMeetName.text = "모임 이름"
            } else if (type == TYPE_MEMO) {
                textMeetName.text = "메모"
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
                if (type == TYPE_TITLE) {
                    myMeetDetailViewModel.updateTitle(
                        newTitle = getEditText(),
                        onSuccess = {
                            loadingAlertProvider.endLoading {
                                dismiss()
                            }
                        },
                        onFail = { msg ->
                            loadingAlertProvider.endLoadingWithMessage(msg)
                        }
                    )
                } else if (type == TYPE_MEMO) {
                    myMeetDetailViewModel.updateDescription(
                        newDescription = getEditText(),
                        onSuccess = {
                            loadingAlertProvider.endLoading {
                                dismiss()
                            }
                        },
                        onFail = { msg ->
                            loadingAlertProvider.endLoadingWithMessage(msg)
                        }
                    )
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