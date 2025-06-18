package com.sooum.where_android.viewmodel.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sooum.domain.model.Friend
import com.sooum.domain.usecase.friend.GetFriendByIdUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFriendByIdUseCase: GetFriendByIdUseCase,
) : ViewModel() {

    private val route =
        savedStateHandle.toRoute<ScreenRoute.HomeRoute.Main.BottomNavigation.FriendMeetDetail>()

    private val findUserId = route.friendId

    private var _friend = MutableStateFlow<Friend?>(null)

    val friend
        get() = _friend.asStateFlow()

    init {
        viewModelScope.launch {
            getFriendByIdUseCase(findUserId).collect {
                _friend.value = it
            }
        }
    }
}