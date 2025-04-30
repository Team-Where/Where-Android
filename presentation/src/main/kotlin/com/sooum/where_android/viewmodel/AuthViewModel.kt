package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.usecase.auth.LoginUseCase
import com.sooum.domain.usecase.auth.SignUpUseCase
import com.sooum.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel(){

    private val _loginState = MutableStateFlow<ApiResult<Any>>(ApiResult.SuccessEmpty)
    val loginState: StateFlow<ApiResult<Any>> = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow<ApiResult<SignUpResult>>(ApiResult.SuccessEmpty)
    val signUpState: StateFlow<ApiResult<SignUpResult>> = _signUpState

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rePassword = MutableStateFlow("")
    val rePassword: StateFlow<String> = _rePassword.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage.asStateFlow()

    private var _isValidName = MutableStateFlow<Boolean?>(null)
    val isValidName: StateFlow<Boolean?> = _isValidName.asStateFlow()

    private val _isValidPassword = MutableStateFlow<Boolean?>(null)
    val isValidPassword: StateFlow<Boolean?> = _isValidPassword.asStateFlow()

    private val _isPasswordMatch = MutableStateFlow<Boolean?>(null)
    val isPasswordMatch: StateFlow<Boolean?> = _isPasswordMatch.asStateFlow()

    private val _isNextButtonEnabled = MutableStateFlow(false)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled.asStateFlow()

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
                email = _email.value,
                password = _password.value,
                name = _name.value,
                profileImage = _profileImage.value
            ).collect { result ->
                _signUpState.value = result

            }
        }
    }

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setName(value: String) {
        _name.value = value
    }

    fun setProfileImage(uri: String) {
        _profileImage.value = uri
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validateInputs()
    }

    fun onRePasswordChanged(rePassword: String) {
        _rePassword.value = rePassword
        validateInputs()
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateInputs()
    }

    fun onNameChanged(name: String){
        _name.value = name
        validateName()
    }

    private fun validateInputs() {
        val passwordValue = _password.value
        val rePasswordValue = _rePassword.value
        val emailValue = _email.value

        val isPasswordValid = if (passwordValue.isEmpty()) null else validatePasswordUseCase(passwordValue)
        val isMatch = if (passwordValue.isEmpty() || rePasswordValue.isEmpty()) null else (passwordValue == rePasswordValue)
        val isEmailNotEmpty = emailValue.isNotEmpty()

        _isValidPassword.value = isPasswordValid
        _isPasswordMatch.value = isMatch
        _isNextButtonEnabled.value = isMatch == true && isPasswordValid == true && isEmailNotEmpty
    }

    private fun validateName() {
        val nameValue = _name.value

        val isName = nameValue.isNotEmpty()
        _isNextButtonEnabled.value = isName == true
    }


}