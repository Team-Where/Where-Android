package com.sooum.where_android.viewmodel.meetdetail

import androidx.annotation.IntRange
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailTabViewModel @Inject constructor(

) : ViewModel() {

    /**
     * 선택된 탭 0 or 1
     * - 0 : 상세
     * - 1 : 장소
     */
    @IntRange(
        from = 0L,
        to = 1L
    )
    var selectedTabPosition: Int = 0


    /**
     * 선택된 placeItem Tab
     * - 0 : All
     * - 1 : Best
     */
    @IntRange(
        from = 0L,
        to = 1L
    )
    var selectedPlaceType: Int = 0

    /**
     * 스크롤된 위치 저장
     */
    var savedScroll: Int = -1

    /**
     * 공유 후 포커싱 된 id
     */
    private var _focusPlaceId = MutableStateFlow(null as Int?)
    fun updateFocusId(placeId: Int) {
        _focusPlaceId.value = placeId
    }

    fun clearFocusId() {
        _focusPlaceId.value = null
    }

    val focusPlaceId
        get() = _focusPlaceId.asStateFlow()
}