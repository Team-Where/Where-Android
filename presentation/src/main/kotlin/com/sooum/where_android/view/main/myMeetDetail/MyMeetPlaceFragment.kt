package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.SelectedPlace
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.AllPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import kotlinx.coroutines.launch

class MyMeetPlaceFragment : MyMeetBaseFragment() {
    private lateinit var binding: FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyMeetPlaceBinding.inflate(inflater, container, false)

        with(binding) {
            icWarning.setOnClickListener { view ->
                TooltipCompat.setTooltipText(view, "친구들과 가기로 결정한 장소 목록입니다")
                view.performLongClick() // 클릭 시 툴팁 표시
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBtn()
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.userAndPlaceMap.collect {
                    allPlaceListAdapter.setData(it)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        val dummyList = listOf(
            SelectedPlace("맛있는 식당", "서울 강남구 테헤란로 123", 5, 12),
            SelectedPlace("한식 전문점", "서울 마포구 홍대입구 456", 3, 8),
            SelectedPlace("카페 모카", "서울 서초구 반포대로 789", 7, 20)
        )
        selectedPlaceListAdapter = SelectedPlaceListAdapter()
        selectedPlaceListAdapter.setList(dummyList)

        allPlaceListAdapter = AllPlaceListAdapter()

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedPlaceListAdapter
        }
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allPlaceListAdapter
        }
    }

    private fun setUpBtn() {
        with(binding) {
            btnAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnBest.isChecked = false
                }
            }

            btnBest.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnAll.isChecked = false
                }
            }
        }
    }


}