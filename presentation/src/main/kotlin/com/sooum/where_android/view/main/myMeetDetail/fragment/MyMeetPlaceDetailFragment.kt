package com.sooum.where_android.view.main.myMeetDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.sooum.where_android.view.common.modal.LoadingAlertProvider
import com.sooum.where_android.view.main.myMeetDetail.common.MyMeetBaseFragment
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailCommentViewModel
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailPlaceViewModel

/**
 * 장소를 눌렀을때 이동되는 화면
 */
class MyMeetPlaceDetailFragment : MyMeetBaseFragment() {

    companion object {
        const val PLACE_ID = "placeId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val placeId = arguments?.getInt(PLACE_ID, 0) ?: 0
        if (placeId < 0) {
            //Error
        } else {
            myMeetDetailCommentViewModel.loadData(placeId)
        }
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                this@MyMeetPlaceDetailFragment.TestContent(
                    myMeetDetailCommentViewModel,
                    myMeetDetailPlaceViewModel,
                    loadingAlertProvider
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myMeetDetailCommentViewModel.clearData()
    }

    @Composable
    fun TestContent(
        commentViewmodel: MyMeetDetailCommentViewModel,
        placeViewModel: MyMeetDetailPlaceViewModel,
        loadingAlertProvider: LoadingAlertProvider,
    ) {
        val placeItem by commentViewmodel.placeItem.collectAsState()
        val commentList by commentViewmodel.commentList.collectAsState()
        Column {
            Text(
                placeItem.toString()
            )
            Button(
                onClick = {
                    loadingAlertProvider.startLoading()
                    placeViewModel.updatePick(
                        placeId = placeItem?.placeId,
                        onSuccess = {
                            loadingAlertProvider.endLoading()
                        },
                        onFail = { msg ->
                            loadingAlertProvider.endLoadingWithMessage(msg)
                        }
                    )
                }
            ) {
                Text("Pick")
            }
            HorizontalDivider()
            LazyColumn {
                items(
                    commentList,
                    key = {
                        it.commentId
                    }
                ) {
                    Text(it.toString())
                }
            }
        }
    }
}