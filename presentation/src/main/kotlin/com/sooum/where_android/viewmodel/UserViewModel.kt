package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import com.sooum.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    fun loadUserList() {
        viewModelScope.launch {
            getUserUseCase().collect {
                _userList.value = it
            }
        }
    }

    companion object {
        //TODO 임시 코드 hilt를 사용하는게 좋아보임
        class ScoreViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if(modelClass.isAssignableFrom(UserViewModel::class.java)){
                    return UserViewModel(GetUserUseCase(userRepository)) as T
                }
                throw IllegalArgumentException("unKnown ViewModel class")
            }
        }
    }
}