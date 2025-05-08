package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.Friend
import com.sooum.domain.usecase.friend.DeleteFriendUseCase
import com.sooum.domain.usecase.friend.GetFriendListUseCase
import com.sooum.domain.usecase.friend.UpdateFriendFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val getUserUseCase: GetFriendListUseCase,
    private val deleteUserUseCase: DeleteFriendUseCase,
    private val updateUserFavoriteUseCase: UpdateFriendFavoriteUseCase,
) : ViewModel() {

    val userList: StateFlow<List<Friend>> =
        getUserUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000L),
                initialValue = emptyList()
            )

    fun deleteFriend(
        friendId: Int
    ) {
        viewModelScope.launch {
            deleteUserUseCase(friendId)
        }
    }

    fun updateFriendFavorite(
        friendId: Int,
        favorite: Boolean
    ) {
        viewModelScope.launch {
            updateUserFavoriteUseCase(friendId, favorite)
        }
    }
}