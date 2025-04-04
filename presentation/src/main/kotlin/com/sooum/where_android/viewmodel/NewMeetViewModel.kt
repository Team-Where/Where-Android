package com.sooum.where_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.NewMeet
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.User
import com.sooum.domain.usecase.GetUserListUseCase
import com.sooum.domain.usecase.meet.AddMeetUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class NewMeetType(
    val order: Int,
    val buttonTitle: String,
    val title: String
) {
    data object Info : NewMeetType(1, "다음", "어떤 모임인가요?")
    data object Friend : NewMeetType(2, "다음", "파티원을 초대해요!")
}


/**
 * [com.sooum.where_android.view.main.newMeet.NewMeetModal]에서 사용되는 viewmodel
 * 새로운 모임 추가를 위한 데아터 처리를 담당한다.
 */
@HiltViewModel
class NewMeetViewModel @Inject constructor(
    getUserListUseCase: GetUserListUseCase,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val addMeetUseCase: AddMeetUseCase
) : ViewModel() {

    private var _viewType: MutableStateFlow<NewMeetType> = MutableStateFlow(NewMeetType.Info)

    val viewType
        get() = _viewType

    fun goStep2() {
        if (newMeetData.image == null) {
            updateImage(ImageAddType.Default)
        }
        _viewType.value = NewMeetType.Friend
    }


    fun goStepResult(
        complete: (ActionResult<NewMeetResult>) -> Unit
    ) {
        viewModelScope.launch() {
            //새로운 모임을 추가하는 시점에서 id값은 null이 아니다
            val id = getLoginUserIdUseCase()!!
            val addResult = addMeetUseCase(
                fromId = id,
                newMeet = newMeetData
            )
            complete(addResult)
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
        NewMeet(
            title = "",
            image = null,
            description = "",
            participants = emptyList()
        )
    )

    fun clear() {
        _viewType.value = NewMeetType.Info
        newMeetData = NewMeet("", "", null)
    }

    fun updateTitle(title: String) {
        newMeetData = newMeetData.copy(title = title)
    }

    fun updateDescription(description: String) {
        newMeetData = newMeetData.copy(description = description)
    }

    fun updateImage(imageAddType: ImageAddType) {
        newMeetData = newMeetData.copy(image = imageAddType)
    }

    fun inviteFriend(user: User) {
        val temp = newMeetData.participants.toMutableList()
        temp.add(user.id.toInt())
        newMeetData = newMeetData.copy(participants = temp)
    }
}