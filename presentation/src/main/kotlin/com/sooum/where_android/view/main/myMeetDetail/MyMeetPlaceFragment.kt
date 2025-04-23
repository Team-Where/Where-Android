package com.sooum.where_android.view.main.myMeetDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.startMapUriOrMarket
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.AllPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.BestPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyMeetPlaceFragment : MyMeetBaseFragment(), PlaceClickCallBack {
    private lateinit var binding: FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter
    private lateinit var bestPlaceListAdapter: BestPlaceListAdapter

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
                myMeetDetailPlaceWithCommentViewModel.placeList.collect {
                    allPlaceListAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceWithCommentViewModel.bestPlaceList.collect {
                    bestPlaceListAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceWithCommentViewModel.pickPlaceList.collect { pickList ->
                    if (pickList.isEmpty()) {
                        binding.placePickItemListView.visibility = View.INVISIBLE
                        binding.placePickItemNoData.visibility = View.VISIBLE
                    } else {
                        binding.placePickItemListView.visibility = View.VISIBLE
                        binding.placePickItemNoData.visibility = View.GONE
                        selectedPlaceListAdapter.submitList(pickList)
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
        bestPlaceListAdapter = BestPlaceListAdapter().apply {
            setCallBack(this@MyMeetPlaceFragment)
        }

        binding.placePickItemListView.adapter = selectedPlaceListAdapter
        binding.placeAllItemListView.adapter = allPlaceListAdapter
        binding.placeBestItemListView.adapter = bestPlaceListAdapter
    }

    private fun setUpBtn() {
        with(binding) {
            btnAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnBest.isChecked = false
                    placeAllItemListView.visibility = View.VISIBLE
                    placeBestItemListView.visibility = View.GONE
                }
            }

            btnBest.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnAll.isChecked = false
                    placeAllItemListView.visibility = View.GONE
                    placeBestItemListView.visibility = View.VISIBLE
                }
            }

            btnPlaceShare.setOnClickListener {
                openMapShareSheet()
            }
        }
    }

    override fun likeChange(placeId: Int) {
        loadingAlertProvider.startLoading()
        myMeetDetailPlaceWithCommentViewModel.likeToggle(
            placeId = placeId,
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