package com.sooum.where_android.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.auth.LoginUseCase
import com.sooum.domain.usecase.friend.LoadFriedListUseCase
import com.sooum.domain.usecase.meet.detail.LoadMeetDetailListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loadMeetDetailListUseCase: LoadMeetDetailListUseCase,
    private val loadFriedListUseCase: LoadFriedListUseCase,
) : ViewModel() {

    /**
     * 로그인 기능
     */
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val id = result.data.userId
                        async {
                            loadFriedListUseCase(id)
                            loadMeetDetailListUseCase(id)
                        }
                        onSuccess()
                    }

                    is ApiResult.Fail.Error -> {
                        onFail("로그인 실패")
                    }

                    else -> {
                        onFail("로그인 실패")
                    }
                }
            }
        }
    }
}