package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.databinding.FragmentEditCommentBinding
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailCommentViewModel
import kotlinx.coroutines.launch

class EditCommentFragment : BottomSheetDialogFragment() {
    private val myMeetDetailCommentViewModel: MyMeetDetailCommentViewModel by activityViewModels()

    companion object {
        private const val ID = "comment_id"

        fun getInstance(
            id: Int,
        ): EditCommentFragment = EditCommentFragment().apply {
            arguments = bundleOf(
                ID to id,
            )
        }
    }

    private var _binding: FragmentEditCommentBinding? = null
    private val binding: FragmentEditCommentBinding
        get() = _binding!!

    private val loadingAlertProvider: LoadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCommentBinding.inflate(inflater, container, false)

        return binding.root
    }

    var comment: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(ID)!!

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailCommentViewModel.commentList.collect { commentItemList ->
                    val item = commentItemList.find { it.commentId == id }
                    with(binding) {

                        if (item != null) {
                            comment = item.description
                            textComment.text = comment
                            btnEdit.isEnabled = true
                        } else {
                            btnEdit.isEnabled = false
                        }
                    }
                }
            }
        }

        with(binding) {

            imageClose.setOnClickListener {
                dismiss()
            }

            btnEdit.setOnClickListener {
                showBottomSheet(
                    EditMyMeetDataFragment.commentEdit(
                        commentId = id,
                        prevData = comment
                    )
                )
            }

            btnDelete.setOnClickListener {
                loadingAlertProvider.startLoading()
                myMeetDetailCommentViewModel.deleteComment(
                    commentId = id,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(parentFragmentManager, fragment::class.java.simpleName)
    }

}
