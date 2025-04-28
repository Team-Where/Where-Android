package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.balloon
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
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 탭에서 장소를 눌렀을때 보이는 화면
 */
@AndroidEntryPoint
class MyMeetPlaceFragment : MyMeetBaseFragment(), PlaceClickCallBack {
    private lateinit var binding: FragmentMyMeetPlaceBinding
    private lateinit var selectedPlaceListAdapter: SelectedPlaceListAdapter
    private lateinit var allPlaceListAdapter: AllPlaceListAdapter
    private lateinit var bestPlaceListAdapter: BestPlaceListAdapter

    @Inject
    lateinit var getLoginUserIdUseCase: GetLoginUserIdUseCase

    private val placeInfoBalloon by balloon<PlaceInfoBalloonFactory>()
    private val placeAddBalloon by balloon<PlaceAddBalloonFactory>()


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

    private var find = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBtn()
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailPlaceViewModel.placeList.collect { placeList ->
                    if (placeList.isEmpty()) {
                        if (!find) {
                            //1회만 작동하도록
                            placeAddBalloon.showAlignBottom(binding.btnPlaceShareIcon)
                            binding.placeItemNoData.visibility = View.VISIBLE
                            find = true
                        }
                    } else {
                        binding.placeItemNoData.visibility = View.GONE
                        allPlaceListAdapter.submitList(placeList)
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
                        bestPlaceListAdapter.submitList(bestPlace)
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
        findNavController().navigate(
            R.id.action_tabFragment_to_PlaceDetailFragment,
            bundleOf(
                MyMeetPlaceDetailFragment.PLACE_ID to placeId
            )
        )
    }
}