package com.sooum.where_android.view.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.InvitedFriend
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentMyMeetDetailBinding
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding

class MyMeetDetailFragment : Fragment() {
    private lateinit var binding : FragmentMyMeetDetailBinding
    private lateinit var adapter: InvitedFriendListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 초기화
        adapter = InvitedFriendListAdapter()

        // RecyclerView 설정
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyMeetDetailFragment.adapter
        }

        // 더미 데이터 추가
        val dummyData = listOf(
            InvitedFriend(R.drawable.test_profile,"김현수"),
            InvitedFriend(R.drawable.test_profile,"박환"),
            InvitedFriend(R.drawable.test_profile,"박한비")

        )

        adapter.setList(dummyData)
    }
}