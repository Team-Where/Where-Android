package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.Friend
import com.sooum.domain.usecase.friend.DeleteFriendUseCase
import com.sooum.domain.usecase.friend.GetFriendListUseCase
import com.sooum.domain.usecase.friend.LoadFriedListUseCase
import com.sooum.domain.usecase.friend.UpdateFriendFavoriteUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val loadFriedListUseCase: LoadFriedListUseCase,
    getFriendListUseCase: GetFriendListUseCase,
    private val deleteUserUseCase: DeleteFriendUseCase,
    private val updateUserFavoriteUseCase: UpdateFriendFavoriteUseCase,
) : ViewModel() {

    val friendList: StateFlow<List<Friend>> =
        getFriendListUseCase()
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
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = updateUserFavoriteUseCase(friendId)) {
                is ActionResult.Success -> {
                    onSuccess()
                }

                is ActionResult.Fail -> {
                    onFail(result.msg)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            getLoginUserIdUseCase()?.let { id ->
                loadFriedListUseCase(id)
            }
        }
    }
}