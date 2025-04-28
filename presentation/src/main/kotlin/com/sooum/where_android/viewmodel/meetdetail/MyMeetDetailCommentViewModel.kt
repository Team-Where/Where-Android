package com.sooum.where_android.viewmodel.meetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.usecase.comment.ClearCommentUseCase
import com.sooum.domain.usecase.comment.GetCommentListUseCase
import com.sooum.domain.usecase.place.GetPlaceByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailCommentViewModel @Inject constructor(
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase,
    getCommentListUseCase: GetCommentListUseCase,
    private val clearCommentUseCase: ClearCommentUseCase,
) : ViewModel() {

    private var _placeItem: MutableStateFlow<PlaceItem?> = MutableStateFlow(null)

    /**
     * 현재 포커싱된 장소 데이터
     */
    val placeItem
        get() = _placeItem

    //모임 상세에 로드된 Comment 목록
    val commentList = getCommentListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )


    fun loadData(
        placeId: Int
    ) {
        viewModelScope.launch {
            getPlaceByIdUseCase(placeId).collect {
                _placeItem.value = it
            }
        }
    }

    fun clearData() {
        viewModelScope.launch {
            _placeItem.value = null
            clearCommentUseCase.invoke()
        }
    }
}

