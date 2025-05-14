package com.sooum.where_android.view.main.myMeetDetail.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.PLACE_STATE_NOT_PICK
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.domain.model.PlaceItem
import com.sooum.where_android.databinding.FragmentMyMeetPlaceDetailBinding
import com.sooum.where_android.startMapUriOrMarket
import com.sooum.where_android.view.main.myMeetDetail.adapter.comment.PlaceCommentListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceDetailClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startKakaoMapUri
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startNaverMapUri
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import kotlinx.coroutines.launch

/**
 * 장소를 눌렀을때 이동되는 화면
 */
class MyMeetPlaceDetailFragment :
    MyMeetBaseFragment<FragmentMyMeetPlaceDetailBinding>(FragmentMyMeetPlaceDetailBinding::inflate),
    PlaceDetailClickCallBack {

    private val placeCommentAdapter = PlaceCommentListAdapter()

    companion object {
        const val PLACE_ID = "placeId"
    }

    override fun initView() {
        val placeId = arguments?.getInt(PLACE_ID, 0) ?: 0
        if (placeId < 0) {
            //Error
        } else {
            myMeetDetailCommentViewModel.loadData(placeId)
        }
        setCommentRecyclerView()

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailCommentViewModel.commentList.collect { commentList ->
                    placeCommentAdapter.submitListWithMineFirst(commentList)
                    myMeetDetailPlaceViewModel.updateCommentCountLocally(
                        placeId = placeId,
                        commentCount = commentList.size
                    )
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailCommentViewModel.placeItem.collect {
                    bindPlaceItem(it)
                }
            }
        }
    }

    private fun setCommentRecyclerView() {
        with(binding.recyclerCommentList) {
            adapter = placeCommentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindPlaceItem(
        placeItem: PlaceItem?
    ) {
        placeItem ?: return

        with(binding) {
            textPlaceName.text = placeItem.place.name
            textPlaceDetail.text = placeItem.place.address
            textCommentNumber.text = placeItem.commentCount.toString()
            textLikeNumber.text = placeItem.likeCount.toString()

            with(placeItem.place.myLike) {
                textLikeNumber.isSelected = this
                textLike.isSelected = this
                iconHeart.isSelected = this
            }
            heartArea.setOnClickListener {
                likeChange(placeItem.place.id)
            }
            btnNaverMap.setOnClickListener {
                startNaverMapUri(placeItem.place.naverLink)
            }
            btnKakaoMap.setOnClickListener {
                startKakaoMapUri(placeItem.place.kakaoLink)
            }
            placeTextContainer.setOnClickListener {
                loadingAlertProvider.startLoading()
                myMeetDetailPlaceViewModel.updatePick(
                    placeId = placeItem.placeId,
                    onSuccess = {
                        loadingAlertProvider.endLoading()
                    },
                    onFail = { msg ->
                        loadingAlertProvider.endLoadingWithMessage(msg)
                    }
                )
            }
            when (placeItem.place.status) {
                PLACE_STATE_PICK -> {
                    imageCheck.isSelected = true
                }

                PLACE_STATE_NOT_PICK -> {
                    imageCheck.isSelected = false
                }

                else -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myMeetDetailCommentViewModel.clearData()
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

}