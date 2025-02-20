package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import com.sooum.domain.usecase.DeleteUserUseCase
import com.sooum.domain.usecase.GetUserUseCase
import com.sooum.domain.usecase.UpdateUserFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
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
        userId: Long
    ) {
        viewModelScope.launch {
            deleteUserUseCase(userId)
        }
    }

    fun updateUserFavorite(
        userId: Long,
        favorite: Boolean
    ) {
        viewModelScope.launch {
            updateUserFavoriteUseCase(userId, favorite)
        }
    }
}