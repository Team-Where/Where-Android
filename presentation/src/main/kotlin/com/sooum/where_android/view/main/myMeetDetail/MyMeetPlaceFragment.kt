package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.SelectedPlace
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.databinding.FragmentOnBoardingFirstBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.InvitedFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.WaitingFriendListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment

class MyMeetPlaceFragment: MyMeetBaseFragment() {
    private lateinit var binding : FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMeetPlaceBinding.inflate(inflater, container, false)

        binding.icWarning.setOnClickListener { view ->
            TooltipCompat.setTooltipText(view, "친구들과 가기로 결정한 장소 목록입니다")
            view.performLongClick() // 클릭 시 툴팁 표시
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedPlaceListAdapter = SelectedPlaceListAdapter()
        setupRecyclerView()

        // 더미 데이터 추가
        val dummyList = listOf(
            SelectedPlace("맛있는 식당", "서울 강남구 테헤란로 123", 5, 12),
            SelectedPlace("한식 전문점", "서울 마포구 홍대입구 456", 3, 8),
            SelectedPlace("카페 모카", "서울 서초구 반포대로 789", 7, 20)
        )
        selectedPlaceListAdapter.setList(dummyList)

    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedPlaceListAdapter
        }
    }
}