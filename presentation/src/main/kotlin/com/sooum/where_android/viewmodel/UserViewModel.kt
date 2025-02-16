package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.User
import com.sooum.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    fun loadUser(id: Int) {
        viewModelScope.launch {
            getUserUseCase().collect {
                _userList.value = it
            }
        }
    }
}