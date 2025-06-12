package com.sooum.where_android.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.auth.CheckEmailUseCase
import com.sooum.domain.usecase.auth.EmailVerifyUseCase
import com.sooum.domain.usecase.auth.RequestEmailAuthUseCase
import com.sooum.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getRequestEmailAuthUseCase: RequestEmailAuthUseCase,
    private val emailVerifyUseCase: EmailVerifyUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
) : ViewModel() {

    var email: String = ""
        private set

    var password: String = ""
        private set

    var name: String = ""
        private set

    var profileImage: String = ""
        private set

    var emailCode: String = ""
        private set

    fun setEmail(value: String) {
        email = value
    }

    fun setPassword(value: String) {
        password = value
    }

    fun setName(value: String) {
        name = value
    }

    fun setProfileImage(value: String) {
        profileImage = value
    }

    fun onEmailVerifyInputChanged(emailValue: String, codeValue: String) {
        email = emailValue
        emailCode = codeValue
    }

    /**
     * 회원가입 기능
     */
    fun signUp(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            signUpUseCase(
                email = email,
                password = password,
                name = name,
                profileImage = profileImage
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onSuccess()
                    }

                    is ApiResult.Fail.Error -> {
                        onFail(result.message ?: "회원가입 실패")
                    }

                    else -> {
                        onFail("회원가입 실패")

                    }
                }
            }
        }
    }

    /**
     * 이메일 중복 확인 및 검증 코드 요청
     */
    fun checkEmailAndAuth(
        email: String,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            checkEmailUseCase(
                email = email
            ).collect { checkResult ->
                when (checkResult) {
                    is ApiResult.Success -> {
                        getRequestEmailAuthUseCase(
                            email = email
                        ).collect { authResult ->
                            when (authResult) {
                                is ApiResult.Success -> {
                                    onSuccess()
                                }

                                is ApiResult.Fail.Error -> {
                                    onFail("인증코드 전송에 실패하였습니다.")
                                }

                                else -> {
                                    onFail("인증코드 전송에 실패하였습니다.")
                                }
                            }
                        }
                    }

                    is ApiResult.Fail.Error -> {
                        onFail(checkResult.message ?: "인증코드 전송에 실패하였습니다.")
                    }

                    else -> {
                        onFail("인증코드 전송에 실패하였습니다.")
                    }
                }
            }
        }
    }

    /**
     * 이메일 검증 코드를 확인
     */
    fun verifyEmailCode(
        email: String,
        code: String,
        onSuccess: (status: String) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            emailVerifyUseCase(
                email = email,
                code = code
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onSuccess(result.data)
                    }

                    is ApiResult.Fail.Error -> {
                        onFail("이메일 인증 요청에 실패했습니다.")
                    }

                    else -> {
                        onFail("이메일 인증 요청에 실패했습니다.")
                    }
                }
            }
        }
    }
}