package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.balloon
import com.sooum.domain.model.PlaceRank
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import com.sooum.where_android.R
import com.sooum.where_android.databinding.FragmentMyMeetPlaceBinding
import com.sooum.where_android.startMapUriOrMarket
import com.sooum.where_android.view.balloon.PlaceAddBalloonFactory
import com.sooum.where_android.view.balloon.PlaceInfoBalloonFactory
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.AllPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.BestPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter.SelectedPlaceListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 탭에서 장소를 눌렀을때 보이는 화면
 */
@AndroidEntryPoint
class MyMeetPlaceFragment : MyMeetBaseFragment<FragmentMyMeetPlaceBinding>(
    FragmentMyMeetPlaceBinding::inflate
), PlaceClickCallBack {
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter
    private lateinit var bestPlaceListAdapter: BestPlaceListAdapter

    @Inject
    lateinit var getLoginUserIdUseCase: GetLoginUserIdUseCase

    private val placeInfoBalloon by balloon<PlaceInfoBalloonFactory>()
    private val placeAddBalloon by balloon<PlaceAddBalloonFactory>()

    private var showPlaceToolTipFinished = false
    private var restoreType: Int = -1

    override fun initView() {
        setupRecyclerView()
        setUpBtn()
        restoreType()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailViewModel.meetDetail.collect { meetDetail ->
                    if (meetDetail != null) {
                        if (meetDetail.finished) {
                            showPlaceToolTipFinished = true
                            binding.btnPlaceShare.visibility = View.INVISIBLE
                        } else {
                            binding.btnPlaceShare.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceViewModel.placeList.collect { placeList ->
                    if (placeList.isEmpty()) {
                        if (!showPlaceToolTipFinished) {
                            //1회만 작동하도록
                            placeAddBalloon.showAlignBottom(binding.btnPlaceShareIcon)
                            binding.placeItemNoData.visibility = View.VISIBLE
                            showPlaceToolTipFinished = true
                        }
                    } else {
                        binding.placeItemNoData.visibility = View.GONE
                        allPlaceListAdapter.submitList(placeList) {
                            if (getSelectedTabState() == 0) {
                                binding.placeAllItemListView.visibility = View.VISIBLE
                                restoreScroll(0)
                            }
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceViewModel.bestPlaceList.collect { bestPlace ->
                    if (bestPlace.isEmpty()) {

                    } else {
                        binding.placeItemNoData.visibility = View.GONE
                        bestPlaceListAdapter.submitList(bestPlace) {
                            if (getSelectedTabState() == 1) {
                                binding.placeBestItemListView.visibility = View.VISIBLE
                                restoreScroll(1)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceViewModel.pickPlaceList.collect { pickList ->
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailTabViewModel.focusPlaceId.collect { focusPlaceId ->
                    //바로 변경하면 submitList와 대기시간이 발생하므로 조금 기다려본다.
                    delay(500L)
                    Log.d("JWHP-1", focusPlaceId.toString())
                    if (focusPlaceId != null) {
                        Log.d("JWHP-2", getSelectedTabState().toString())
                        when (getSelectedTabState()) {
                            0 -> {
                                val findIndex =
                                    allPlaceListAdapter.currentList.indexOfFirst { it.placeId == focusPlaceId }
                                Log.d("JWHP-3", "findIndex : $findIndex")
                                if (findIndex >= 0) {
                                    val holder =
                                        binding.placeAllItemListView.findViewHolderForAdapterPosition(
                                            findIndex
                                        )
                                    holder?.itemView?.y?.toInt()?.let { destY ->
                                        binding.root.smoothScrollTo(0, destY)
                                    }
                                }
                            }

                            1 -> {
                                val findIndex =
                                    bestPlaceListAdapter.currentList.indexOfFirst { it is PlaceRank.PostItem && it.place.placeId == focusPlaceId }
                                Log.d("JWHP-3", "findIndex : $findIndex")
                                if (findIndex >= 0) {
                                    val holder =
                                        binding.placeBestItemListView.findViewHolderForAdapterPosition(
                                            findIndex
                                        )
                                    holder?.itemView?.y?.toInt()?.let { destY ->
                                        binding.root.smoothScrollTo(0, destY)
                                    }
                                }
                            }
                        }
                        myMeetDetailTabViewModel.clearFocusId()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        selectedPlaceListAdapter = SelectedPlaceListAdapter(
            this@MyMeetPlaceFragment
        )

        allPlaceListAdapter = AllPlaceListAdapter(
            this@MyMeetPlaceFragment
        )

        bestPlaceListAdapter = BestPlaceListAdapter(
            this@MyMeetPlaceFragment
        )

        binding.placePickItemListView.adapter = selectedPlaceListAdapter
        binding.placeAllItemListView.adapter = allPlaceListAdapter
        binding.placeBestItemListView.adapter = bestPlaceListAdapter
    }

    private fun setUpBtn() {
        with(binding) {
            btnAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnBest.isChecked = false
                    if (allPlaceListAdapter.itemCount == 0) {
                        placeItemNoData.visibility = View.VISIBLE
                        placeAllItemListView.visibility = View.GONE
                        placeBestItemListView.visibility = View.GONE
                    } else {
                        placeItemNoData.visibility = View.GONE
                        placeAllItemListView.visibility = View.VISIBLE
                        placeBestItemListView.visibility = View.GONE
                    }
                }
            }

            btnBest.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    btnAll.isChecked = false
                    if (bestPlaceListAdapter.itemCount == 0) {
                        placeItemNoData.visibility = View.VISIBLE
                        placeAllItemListView.visibility = View.GONE
                        placeBestItemListView.visibility = View.GONE
                    } else {
                        placeItemNoData.visibility = View.GONE
                        placeAllItemListView.visibility = View.GONE
                        placeBestItemListView.visibility = View.VISIBLE
                    }
                }
            }

            btnPlaceShare.setOnClickListener {
                openMapShareSheet()
            }

            icWarning.setOnClickListener {
                placeInfoBalloon.showAlignBottom(icWarning)
            }
        }
    }

    /**
     * 마지막 상태를 복구한다.
     */
    private fun restoreType() {
        with(myMeetDetailTabViewModel) {
            when (selectedPlaceType) {
                0 -> {
                    binding.btnAll.isChecked = true
                    restoreType = 0
                }

                1 -> {
                    binding.btnBest.isChecked = true
                    restoreType = 1
                }
            }
        }
    }

    /**
     * 현재 탭의 state을 가져온다.
     */
    private fun getSelectedTabState(): Int {
        with(binding) {
            return if (btnAll.isChecked) {
                0
            } else if (btnBest.isChecked) {
                1
            } else {
                -1
            }
        }
    }

    /**
     * 복구할 scroll 위차가 있다면 복구한다.
     */
    private fun restoreScroll(type: Int) {
        if (type == restoreType) {
            with(myMeetDetailTabViewModel) {
                if (savedScroll > 0) {
                    val temp = savedScroll
                    binding.root.post {
                        binding.root.scrollTo(0, temp)
                    }
                    savedScroll = -1
                }
            }
        }
    }

    override fun likeChange(placeId: Int) {
        loadingAlertProvider.startLoading()
        myMeetDetailPlaceViewModel.likeToggle(
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

    override fun clickPlace(placeId: Int) {
        with(myMeetDetailTabViewModel) {
            selectedPlaceType = getSelectedTabState()
            savedScroll = binding.root.scrollY
        }
        findNavController().navigate(
            R.id.action_tabFragment_to_PlaceDetailFragment,
            bundleOf(
                MyMeetPlaceDetailFragment.PLACE_ID to placeId
            )
        )
    }
}