package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.PlaceList
import com.sooum.domain.model.SelectedPlace
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.AllPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.InvitedFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.WaitingFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment

class MyMeetPlaceFragment: MyMeetBaseFragment() {
    private lateinit var binding : FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetPlaceBinding.inflate(inflater, container, false)

        with(binding){
            icWarning.setOnClickListener { view ->
                TooltipCompat.setTooltipText(view, "친구들과 가기로 결정한 장소 목록입니다")
                view.performLongClick() // 클릭 시 툴팁 표시
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyList = listOf(
            SelectedPlace("맛있는 식당", "서울 강남구 테헤란로 123", 5, 12),
            SelectedPlace("한식 전문점", "서울 마포구 홍대입구 456", 3, 8),
            SelectedPlace("카페 모카", "서울 서초구 반포대로 789", 7, 20)
        )

        val postList = listOf(
            PlaceList.ProfileHeader(userId = "me", userName = "나", profileImage = "my_profile_url"),
            PlaceList.PostItem(userId = "me", title = "무드서울", location = "서울 용산구 한강대로21길 18 1층"),
            PlaceList.PostItem(userId = "me", title = "모닝강카페랩", location = "서울 용산구 한강대로21길 18 1층"),

            PlaceList.ProfileHeader(userId = "other", userName = "조나월드", profileImage = "other_profile_url"),
            PlaceList.PostItem(userId = "other", title = "모닝강카페랩", location = "서울 용산구 한강대로21길 18 1층"),
            PlaceList.PostItem(userId = "other", title = "모닝강카페랩", location = "서울 용산구 한강대로21길 18 1층")
        )

        selectedPlaceListAdapter = SelectedPlaceListAdapter()
        allPlaceListAdapter = AllPlaceListAdapter(postList)
        setupRecyclerView()

        selectedPlaceListAdapter.setList(dummyList)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedPlaceListAdapter
        }
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allPlaceListAdapter
        }
    }

}