package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skydoves.balloon.balloon
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.PLACE_STATE_NOT_PICK
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.model.ProfileImage
import com.sooum.where_android.databinding.FragmentMyMeetPlaceDetailBinding
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.startMapUriOrMarket
import com.sooum.where_android.view.balloon.PlacePickBalloonFactory
import com.sooum.where_android.view.common.modal.DeleteModalFragment
import com.sooum.where_android.view.main.myMeetDetail.adapter.comment.PlaceCommentListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.image.ImageListAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.OverlapDecoration
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceDetailClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startKakaoMapUri
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startNaverMapUri
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.EditCommentFragment
import com.sooum.where_android.view.main.myMeetDetail.modal.EditMyMeetDataFragment
import kotlinx.coroutines.launch


/**
 * 장소를 눌렀을때 이동되는 화면
 */
class MyMeetPlaceDetailFragment :
    MyMeetBaseFragment<FragmentMyMeetPlaceDetailBinding>(FragmentMyMeetPlaceDetailBinding::inflate),
    PlaceDetailClickCallBack,
    DeleteModalFragment.DeleteModalAction {

    private val placeCommentAdapter = PlaceCommentListAdapter { commentItem ->
        onCommentClicked(commentItem)
    }

    private val imageListAdapter = ImageListAdapter()

    companion object {
        const val PLACE_ID = "placeId"
    }

    private var placeId: Int = -1
    override fun initView() {
        placeId = arguments?.getInt(PLACE_ID, 0) ?: -1
        if (placeId <= 0) {
            //Error
            showSimpleToast("잘못된 접근 입니다.")
            findNavController().popBackStack()
            return
        }

        myMeetDetailCommentViewModel.loadData(placeId)

        setCommentRecyclerView()
        setProfileImageRecyclerView()

        with(binding) {
            imageBack.setOnClickListener { findNavController().popBackStack() }
            textDelete.setOnClickListener {
                showBottomSheet(
                    DeleteModalFragment.getInstance(
                        buttonText = "장소 삭제",
                        action = this@MyMeetPlaceDetailFragment
                    ),
                    DeleteModalFragment.TAG
                )
            }
            btnAddComment.setOnClickListener {
                showBottomSheet(
                    EditMyMeetDataFragment.commentAdd()
                )
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myMeetDetailCommentViewModel.commentList.collect { commentList ->
                    placeCommentAdapter.submitListWithMineFirst(commentList)

                    //내 코멘트가 있는지 확인
                    if (commentList.any { it.isMine }) {
                        //있다면 숨김
                        binding.btnAddComment.visibility = View.GONE
                    } else {
                        //없으면 보여줌
                        binding.btnAddComment.visibility = View.VISIBLE
                    }

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

    private fun onCommentClicked(commentItem: CommentListItem) {
        showBottomSheet(
            EditCommentFragment.getInstance(
                id = commentItem.commentId,
            )
        )
    }

    private fun setCommentRecyclerView() {
        with(binding.recyclerCommentList) {
            adapter = placeCommentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setProfileImageRecyclerView() {
        with(binding.imageRecyclerView) {
            adapter = imageListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(OverlapDecoration(overlapWidth = 40))
        }
    }

    private var showOnlyOneBalloon: Boolean = false
    private val placePickBalloon by balloon<PlacePickBalloonFactory>()

    private fun bindPlaceItem(
        placeItem: PlaceItem?
    ) {
        placeItem ?: return

        if (!showOnlyOneBalloon) {
            when (placeItem.place.status) {
                PLACE_STATE_PICK -> {
                    showOnlyOneBalloon = true
                }

                PLACE_STATE_NOT_PICK -> {
                    placePickBalloon.showAlignBottom(
                        binding.imageCheck
                    )
                    showOnlyOneBalloon = true
                }

                else -> {

                }
            }
        }

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
                    if (placePickBalloon.isShowing) {
                        placePickBalloon.dismiss()
                    }
                    imageCheck.isSelected = true
                }

                PLACE_STATE_NOT_PICK -> {
                    imageCheck.isSelected = false
                }

                else -> {

                }
            }
            imageListAdapter.submitList(placeItem.place.users.map { ProfileImage(it) })
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

    private fun showBottomSheet(fragment: BottomSheetDialogFragment, tag: String? = null) {
        fragment.show(parentFragmentManager, tag ?: fragment::class.java.simpleName)
    }

    override fun onDelete() {
        loadingAlertProvider.startLoading()
        myMeetDetailPlaceViewModel.deletePlace(
            placeId = placeId,
            onSuccess = {
                loadingAlertProvider.endLoading {
                    findNavController().popBackStack()
                }
            },
            onFail = { msg ->
                loadingAlertProvider.endLoadingWithMessage(msg)
            }
        )
    }
}