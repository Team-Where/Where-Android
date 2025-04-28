package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentMyMeetDetailBinding
import com.sooum.where_android.view.common.modal.ImagePickerDialogFragment
import com.sooum.where_android.view.main.myMeetDetail.adapter.firend.InvitedFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.firend.WaitingFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.MeetCoverDialog
import com.sooum.where_android.view.widget.CoverImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 탭에서 상세를 눌렀을때 보이는 화면
 */
@AndroidEntryPoint
class MyMeetDetailFragment : MyMeetBaseFragment(),
    InvitedFriendListAdapter.OnItemClickEventListener,
    MeetCoverDialog.CoverActionHandler,
    ImagePickerDialogFragment.ImageTypeHandler {
    private lateinit var binding: FragmentMyMeetDetailBinding
    private lateinit var invitedFriendAdapter: InvitedFriendListAdapter
    private lateinit var waitingFriendListAdapter: WaitingFriendListAdapter

    private var imagePickerDialog: ImagePickerDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyMeetDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.meetDetail.collect {
                    setData(meetDetail = it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.invitedFriendList.collect { list ->
                    binding.tvFriendNumber.text = list.size.toString()
                    invitedFriendAdapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.waitingFriendList.collect { list ->
                    waitingFriendListAdapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceViewModel.placeCount.collect { placeCount ->
                    binding.tvLocationNumber.text = placeCount.toString()
                }
            }
        }

        setButtonAction()
    }

    private fun setButtonAction() {
        with(binding) {
            btnLocation.setOnClickListener {
                openMapShareSheet()
            }
            btnSchedule.setOnClickListener {
                findNavController().navigate(
                    R.id.action_tabFragment_to_ScheduleFragment
                )
            }
            btnFriend.setOnClickListener {
                findNavController().navigate(
                    R.id.action_tabFragment_to_InviteFriendFragment
                )
            }
            groupImage.setOnClickListener {
                myMeetDetailViewModel.meetDetail.value?.let {
                    MeetCoverDialog.getInstance(
                        imageLink = it.image,
                        handler = this@MyMeetDetailFragment
                    ).show(
                        parentFragmentManager, MeetCoverDialog.TAG
                    )
                }
            }

            btnFinishMeet.setOnClickListener {
                loadingAlertProvider.startLoading()
                myMeetDetailViewModel.finishMeet(
                    onSuccess = {
                        loadingAlertProvider.endLoading()
                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                    }
                )
            }
            finishOpacity.setOnClickListener {
                //Block Touch
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imagePickerDialog = null
    }

    private fun setupRecyclerView() {
        invitedFriendAdapter = InvitedFriendListAdapter().apply {
            setItemClickListener(this@MyMeetDetailFragment)
        }
        waitingFriendListAdapter = WaitingFriendListAdapter()
        with(binding) {
            invitedFriendList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = invitedFriendAdapter
            }
            waitingFriendList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = waitingFriendListAdapter
            }
        }
    }

    private fun setData(meetDetail: MeetDetail?) {
        meetDetail ?: return
        with(binding) {
            groupImage.setContent {
                CoverImageView(
                    src = meetDetail.image,
                    size = 64.dp,
                    radius = 5.dp
                )
            }

            groupTitle.text = meetDetail.title
            groupDescription.text = meetDetail.description

            meetDetail.schedule?.let { schedule ->
                tvSchedule.text = schedule.makeScheduleText()
                btnSchedule.text = "일정 수정"
            } ?: {
                tvSchedule.text = "아직 정해진 일정이 없어요"
                btnSchedule.text = "일정 등록"
            }

            if (meetDetail.finished) {
                finishOpacity.visibility = View.VISIBLE
                finishContainer.visibility = View.VISIBLE
                btnFinishMeet.visibility = View.GONE
            } else {
                finishOpacity.visibility = View.GONE
                finishContainer.visibility = View.GONE
                btnFinishMeet.visibility = View.VISIBLE
            }
        }
    }

    override fun clickedUserIcon(item: InvitedFriend) {
        //TODO
        openMapShareSheet()
    }


    override fun changeCover() {
        if (imagePickerDialog == null) {
            imagePickerDialog = ImagePickerDialogFragment.getInstance(
                handler = this@MyMeetDetailFragment,
                maxImage = 1
            )
        }
        imagePickerDialog?.show(parentFragmentManager, ImagePickerDialogFragment.TAG)
    }

    override fun receiveImageType(imageType: ImageAddType) {
        loadingAlertProvider.startLoading()
        myMeetDetailViewModel.updateImage(
            imageType,
            onSuccess = {
                loadingAlertProvider.endLoading {
                    imagePickerDialog?.dismiss()
                }
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
            }
        )
    }
}

private fun Schedule.makeScheduleText(): String {
    val time = hour
    return String.format(
        "%d.%d.%d %s %d시",
        year,
        month,
        day,
        if (time > 12) "오후" else "오전",
        if (time > 12) time - 12 else time
    )
}