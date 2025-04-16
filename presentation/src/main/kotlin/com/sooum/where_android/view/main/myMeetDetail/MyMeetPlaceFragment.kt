package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sooum.domain.model.Place
import com.sooum.domain.model.SelectedPlace
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.startMapUriOrMarket
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.AllPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.PlaceClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyMeetPlaceFragment : MyMeetBaseFragment(), PlaceClickCallBack {
    private lateinit var binding: FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter

    @Inject
    lateinit var getLoginUserIdUseCase: GetLoginUserIdUseCase

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.pickPlaceList.collect { pickList ->
                    val pickList = pickList.toMutableList()
                    pickList.add(
                        Place(
                            999,
                            "",
                            "",
                            "QWER",
                            "ASDF",
                            44,
                            false,
                            "Picked",
                            true
                        )
                    )
                    if (pickList.isEmpty()) {
                        binding.placePickItemListView.visibility = View.INVISIBLE
                        binding.placePickItemNoData.visibility = View.VISIBLE
                    } else {
                        binding.placePickItemListView.visibility = View.VISIBLE
                        binding.placePickItemNoData.visibility = View.GONE
                        selectedPlaceListAdapter.setData(pickList.map { SelectedPlace(it) })
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        selectedPlaceListAdapter = SelectedPlaceListAdapter().apply {
            setCallBack(this@MyMeetPlaceFragment)
        }
        allPlaceListAdapter = AllPlaceListAdapter().apply {
            setCallBack(this@MyMeetPlaceFragment)
        }

        binding.placePickItemListView.adapter = selectedPlaceListAdapter
        binding.placeAllItemListView.adapter = allPlaceListAdapter
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

    override fun likeChange(placeId: Int) {
        loadingAlertProvider.startLoading()
        myMeetDetailViewModel.likeToggle(
            placeId,
            onSuccess = {
                loadingAlertProvider.endLoading()
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
            }
        )
    }

    override fun startMapUri(uriString: String, marketPackage: String) {
        context?.startMapUriOrMarket(uriString, marketPackage)
    }
}