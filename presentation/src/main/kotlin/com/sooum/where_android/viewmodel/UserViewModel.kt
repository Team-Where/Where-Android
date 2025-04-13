package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.User
import com.sooum.domain.usecase.DeleteUserUseCase
import com.sooum.domain.usecase.GetUserListUseCase
import com.sooum.domain.usecase.UpdateUserFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserListUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserFavoriteUseCase: UpdateUserFavoriteUseCase,
) : ViewModel() {

    val userList: StateFlow<List<User>> =
        getUserUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000L),
                initialValue = emptyList()
            )

    fun deleteUser(
        userId: Int
    ) {
        viewModelScope.launch {
            deleteUserUseCase(userId)
        }
    }

    fun updateUserFavorite(
        userId: Int,
        favorite: Boolean
    ) {
        viewModelScope.launch {
            updateUserFavoriteUseCase(userId, favorite)
        }
    }
}