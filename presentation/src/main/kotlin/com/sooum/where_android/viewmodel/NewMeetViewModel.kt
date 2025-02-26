package com.sooum.where_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.NewMeet
import com.sooum.domain.model.User
import com.sooum.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class NewMeetType(
    val order: Int,
    val buttonTitle: String,
    val title: String
) {
    data object Info : NewMeetType(1, "다음", "어떤 모임인가요?")
    data object Friend : NewMeetType(2, "다음", "파티원을 초대해요!")
}


@HiltViewModel
class NewMeetViewModel @Inject constructor(
    getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private var _viewType: MutableStateFlow<NewMeetType> = MutableStateFlow(NewMeetType.Info)

    val viewType
        get() = _viewType

    fun nextViewType(
        complete: (NewMeet) -> Unit
    ) {
        if (_viewType.value == NewMeetType.Info) {
            //다음 스텝으로 넘어갈때 이미지갓 선택되었는지 여부
            if (newMeetData.image == null) {
                updateImage(ImageAddType.Default)
            }
            _viewType.value = NewMeetType.Friend
        } else {
            complete(newMeetData)
        }
    }

    val userList: StateFlow<List<User>> =
        getUserListUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )


    var newMeetData by mutableStateOf(
        NewMeet("", null)
    )

    fun clear() {
        _viewType.value = NewMeetType.Info
        newMeetData = NewMeet("", null)
    }

    fun updateTitle(title: String) {
        newMeetData = newMeetData.copy(title = title)
    }

    fun updateImage(imageAddType: ImageAddType) {
        newMeetData = newMeetData.copy(image = imageAddType)
    }
}