package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.Image
import coil3.load
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentMyMeetDetailBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.InvitedFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.WaitingFriendListAdapter
import com.sooum.where_android.viewmodel.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyMeetDetailFragment : Fragment() {
    private lateinit var binding: FragmentMyMeetDetailBinding
    private lateinit var invitedFriendAdapter: InvitedFriendListAdapter
    private lateinit var waitingFriendListAdapter: WaitingFriendListAdapter

    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invitedFriendAdapter = InvitedFriendListAdapter()
        waitingFriendListAdapter = WaitingFriendListAdapter()
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            //Data init
            myMeetDetailViewModel.meetDetail.collect {
                setData(meetDetail = it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = invitedFriendAdapter
        }

        binding.recyclerViewJoined.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = waitingFriendListAdapter
        }
    }

    private fun setData(meetDetail: MeetDetail?) {
        meetDetail ?: return
        binding.groupImage.load(meetDetail.image) {
            this.placeholder(R.drawable.image_meet_default_cover)
            this.error(R.drawable.image_meet_default_cover)
        }
        binding.groupTitle.text = meetDetail.title
        binding.groupDescription.text = meetDetail.description

        binding.tvSchedule.text = meetDetail.makeScheduleText()
    }
}

private fun MeetDetail.makeScheduleText(): String {
    return String.format(
        "%d.%d.%d %s %d시",
        year,
        month,
        day,
        if (time > 12) "오후" else "오전",
        if (time > 12) time - 12 else time
    )
}