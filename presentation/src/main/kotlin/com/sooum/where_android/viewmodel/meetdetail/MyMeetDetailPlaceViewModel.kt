package com.sooum.where_android.viewmodel.meetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.model.PlaceRank
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.place.AddPlaceUseCase
import com.sooum.domain.usecase.place.DeletePlaceUseCase
import com.sooum.domain.usecase.place.GetMeetPlaceListUseCase
import com.sooum.domain.usecase.place.TogglePlaceLikeUseCase
import com.sooum.domain.usecase.place.UpdatePlaceCommentCountLocalUseCase
import com.sooum.domain.usecase.place.UpdatePlacePickUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailPlaceViewModel @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    getMeetPlaceListUseCase: GetMeetPlaceListUseCase,
    private val addPlaceUseCase: AddPlaceUseCase,
    private val deletePlaceUseCase: DeletePlaceUseCase,
    private val togglePlaceLikeUseCase: TogglePlaceLikeUseCase,
    private val updatePlacePickUseCase: UpdatePlacePickUseCase,
    private val updatePlaceCommentCountLocalUseCase: UpdatePlaceCommentCountLocalUseCase
) : ViewModel() {

    //모임 상세에 로드된 place 목록
    val placeList = getMeetPlaceListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    //plceMap에있는 총 장소 수
    val placeCount = placeList.transform {
        emit(it.size)
    }

    //pick된 장소
    val pickPlaceList = placeList.transform { places ->
        val filterList = places.filter { it.status == PLACE_STATE_PICK }
        emit(filterList.sortedByDescending { it.likeCount })
    }

    //상위 3개의 장소
    val bestPlaceList = placeList.transform { placeItems ->
        val sorted = placeItems.sortedByDescending { it.likeCount }

        val result = mutableListOf<PlaceRank>()
        var currentRank = 0
        var lastLikeCount: Int? = null

        val groupedByRank = linkedMapOf<Int, MutableList<PlaceItem>>()

        //3위까지 찾기 시작
        for (placeItem in sorted) {
            if (placeItem.likeCount != lastLikeCount) {
                currentRank += 1
                lastLikeCount = placeItem.likeCount
            }

            if (currentRank > 3) break

            groupedByRank.getOrPut(currentRank) { mutableListOf() }.add(placeItem)
        }

        //rank별 아이템 생성
        for ((rank, placesInRank) in groupedByRank) {
            result.add(PlaceRank.RankHeader(rank))
            placesInRank.forEach { place ->
                result.add(PlaceRank.PostItem(rank, place))
            }
        }

        emit(result)
    }

    private var meetDetailId: Int? = null

    fun loadData(
        meetDetailId: Int
    ) {
        this.meetDetailId = meetDetailId
    }

    fun addPlace(
        shareResult: ShareResult,
        onSuccess: (placeId: Int) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            meetDetailId?.let {
                when (val result = addPlaceUseCase(it, getLoginUserIdUseCase()!!, shareResult)) {
                    is ActionResult.Success -> {
                        onSuccess(result.data)
                    }

                    is ActionResult.Fail -> {
                        onFail(result.msg)
                    }
                }
            }
        }
    }

    fun deletePlace(
        placeId: Int?,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        if (placeId == null) {
            onFail("not Match placeId")
        } else {
            viewModelScope.launch {
                when (val result = deletePlaceUseCase(placeId)) {
                    is ActionResult.Success -> {
                        onSuccess()
                    }

                    is ActionResult.Fail -> {
                        onFail(result.msg)
                    }
                }
            }
        }
    }

    fun likeToggle(
        placeId: Int,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = togglePlaceLikeUseCase(placeId)) {
                is ActionResult.Success -> {
                    onSuccess()
                }

                is ActionResult.Fail -> {
                    onFail(result.msg)
                }
            }
        }
    }

    fun updatePick(
        placeId: Int?,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        if (placeId == null) {
            onFail("not Match placeId")
        } else {
            viewModelScope.launch {
                when (val result = updatePlacePickUseCase(placeId)) {
                    is ActionResult.Success -> {
                        onSuccess()
                    }

                    is ActionResult.Fail -> {
                        onFail(result.msg)
                    }
                }
            }
        }
    }

    fun updateCommentCountLocally(
        placeId: Int?,
        commentCount: Int
    ) {
        if (placeId == null) {

        } else {
            viewModelScope.launch {
                updatePlaceCommentCountLocalUseCase(
                    placeId,
                    commentCount
                )
            }
        }
    }
}

