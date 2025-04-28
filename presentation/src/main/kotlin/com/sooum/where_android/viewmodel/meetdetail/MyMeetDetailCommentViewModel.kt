package com.sooum.where_android.viewmodel.meetdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailCommentViewModel @Inject constructor(

) : ViewModel() {

//    //모임 상세에 로드된 place 목록
//    val commentList = getMeetPlaceListUseCase()
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000L),
//            emptyList()
//        )
//

    fun loadData(
        placeId: Int
    ) {

    }
}

