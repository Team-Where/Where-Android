package com.sooum.where_android.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CheckEmail
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.usecase.auth.CheckEmailUseCase
import com.sooum.domain.usecase.auth.EmailVerifyUseCase
import com.sooum.domain.usecase.auth.LoginUseCase
import com.sooum.domain.usecase.auth.RequestEmailAuthUseCase
import com.sooum.domain.usecase.auth.SignUpUseCase
import com.sooum.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val getRequestEmailAuthUseCase: RequestEmailAuthUseCase,
    private val emailVerifyUseCase: EmailVerifyUseCase,
    private val checkEmailUseCase: CheckEmailUseCase
) : ViewModel(){

    private val _loginState = MutableStateFlow<ApiResult<Any>>(ApiResult.SuccessEmpty)
    val loginState: StateFlow<ApiResult<Any>> = _loginState.asStateFlow()
  
    private val _emailRequestState = MutableStateFlow<ApiResult<Unit>>(ApiResult.SuccessEmpty)
    val emailRequestState: StateFlow<ApiResult<Unit>> = _emailRequestState.asStateFlow()

    private val _emailVerifyState = MutableSharedFlow<ApiResult<String>>(replay = 0)
    val emailVerifyState: SharedFlow<ApiResult<String>> = _emailVerifyState

    private val _signUpState = MutableStateFlow<ApiResult<SignUpResult>>(ApiResult.SuccessEmpty)
    val signUpState: StateFlow<ApiResult<SignUpResult>> = _signUpState

    private val _checkEmailState = MutableSharedFlow<ApiResult<Unit>>(replay = 0)
    val checkEmailState: SharedFlow<ApiResult<Unit>> = _checkEmailState

    private val _emailInput = MutableStateFlow("")
    val emailInput: StateFlow<String> = _emailInput

    fun onEmailInputChanged(input: String) {
        _emailInput.value = input
    }

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
     * 로그인 기능
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            loginUseCase(email, password).collect { result ->
                _loginState.value = result
            }
        }
    }

    /**
     * 회원가입 기능
     */
    fun signUp(
    ) {
        viewModelScope.launch {
            signUpUseCase(
                email = email,
                password = password,
                name = name,
                profileImage = profileImage
            ).collect { result ->
                _signUpState.value = result
            }
        }
    }

    /**
     * 이메일 중복 확인 기능
     */
    fun checkEmail(email: String) {
      viewModelScope.launch {
          checkEmailUseCase(
              email = email
          ).collect{ result ->
              _checkEmailState.emit(result)
          }
      }
    }

    /**
     * 이메일 검증 코드를 요청
     */
    fun getEmailAuth(email: String){
        viewModelScope.launch {
            getRequestEmailAuthUseCase(
                email = email
            ).collect{ result ->
               _emailRequestState.value = result

                if (result is ApiResult.Success || result is ApiResult.Fail) {
                    _emailRequestState.value = ApiResult.SuccessEmpty
                }
            }
        }
    }

    /**
     * 이메일 검증 코드를 확인
     */
    fun verifyEmailCode(email: String, code: String){
        viewModelScope.launch {
            emailVerifyUseCase(
                email = email,
                code = code
            ).collect{ result ->
                _emailVerifyState.emit(result)

                if (result is ApiResult.Success || result is ApiResult.Fail) {
                    _emailRequestState.value = ApiResult.SuccessEmpty
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _emailInput
                .debounce(300) // 입력 후 300ms 동안 정지하면 실행
                .filter { it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches() }
                .distinctUntilChanged()
                .collectLatest { email ->
                    checkEmail(email)
                }
        }
    }
}