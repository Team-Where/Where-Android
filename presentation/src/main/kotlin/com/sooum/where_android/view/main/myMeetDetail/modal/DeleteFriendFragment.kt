package com.sooum.where_android.view.main.myMeetDetail.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentDeleteFriendBinding
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailViewModel

class DeleteFriendFragment : BottomSheetDialogFragment() {
    companion object {
        fun getInstance(
            friendId: Int
        ): DeleteFriendFragment {
            return DeleteFriendFragment().apply {
                arguments = bundleOf(
                    FRIEND_ID to friendId
                )
            }
        }

        const val TAG = "DeleteFriendFragment"

        private const val FRIEND_ID = "friendId"
    }

    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    private var _binding: FragmentDeleteFriendBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogRoundStyle
    }

    private val loadingAlertProvider by lazy {
        LoadingAlertProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendId = arguments?.getInt(FRIEND_ID)
        with(binding) {
            btnDeleteFriend.setOnClickListener {
                //TODO delete Action with friendId
            }
            imageClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}