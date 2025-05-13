package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.PLACE_STATE_NOT_PICK
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.where_android.databinding.FragmentComposeBinding
import com.sooum.where_android.databinding.FragmentMyMeetPlaceDetailBinding
import com.sooum.where_android.view.balloon.PlacePickBalloonFactory
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.myMeetDetail.adapter.comment.PlaceCommentListAdapter
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailCommentViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailPlaceViewModel
import java.sql.Ref

/**
 * 장소를 눌렀을때 이동되는 화면
 */
class MyMeetPlaceDetailFragment :
    MyMeetBaseFragment<FragmentMyMeetPlaceDetailBinding>(FragmentMyMeetPlaceDetailBinding::inflate) {

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

        val dummyComments = listOf(
            CommentListItem(
                commentId = 1,
                placeId = 123,
                description = "정말 분위기 좋아요!",
                createdAt = "2025-05-13 10:00",
                isMine = false
            ),
            CommentListItem(
                commentId = 2,
                placeId = 123,
                description = "친절하고 맛있었어요.",
                createdAt = "2025-05-13 10:05",
                isMine = true
            ),
        )

        setCommentRecyclerView(dummyComments)
    }

    private fun setCommentRecyclerView(commentList: List<CommentListItem>){
        val adapter = PlaceCommentListAdapter()
        binding.recyclerCommentList.adapter = adapter
        binding.recyclerCommentList.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitListWithMineFirst(commentList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myMeetDetailCommentViewModel.clearData()
    }

}