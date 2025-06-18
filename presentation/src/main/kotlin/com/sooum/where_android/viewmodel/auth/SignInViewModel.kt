package com.sooum.where_android.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.LoadDataWhenLoginUseCase
import com.sooum.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loadDataWhenLoginUserCase: LoadDataWhenLoginUseCase,
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
                            loadDataWhenLoginUserCase(id)
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