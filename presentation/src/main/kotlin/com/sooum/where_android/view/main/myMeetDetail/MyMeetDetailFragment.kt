package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.size.Scale
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentMyMeetDetailBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.InvitedFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.WaitingFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.widget.CoverImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyMeetDetailFragment : MyMeetBaseFragment(),
    InvitedFriendListAdapter.OnItemClickEventListener {
    private lateinit var binding: FragmentMyMeetDetailBinding
    private lateinit var invitedFriendAdapter: InvitedFriendListAdapter
    private lateinit var waitingFriendListAdapter: WaitingFriendListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    invitedFriendAdapter.setList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.waitingFriendList.collect { list ->
                    waitingFriendListAdapter.setList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.placeCount.collect { placeCount ->
                    binding.tvLocationNumber.text = placeCount.toString()
                }
            }
        }

        with(binding) {
            btnLocation.setOnClickListener {
                openMapShareSheet()
            }
            btnSchedule.setOnClickListener {
                findNavController(view).navigate(
                    R.id.action_tabFragment_to_ScheduleFragment
                )
            }
            btnFriend.setOnClickListener {
                findNavController(view).navigate(
                    R.id.action_tabFragment_to_InviteFriendFragment
                )
            }
        }
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
                meetDetail.CoverImage(size = 64.dp, radius = 5.dp)
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
        }
    }

    override fun clicked(item: InvitedFriend) {
        openMapShareSheet()
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