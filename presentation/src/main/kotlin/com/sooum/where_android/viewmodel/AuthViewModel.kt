package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sooum.domain.usecase.meet.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel(){
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rePassword = MutableStateFlow("")
    val rePassword: StateFlow<String> = _rePassword.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _isValidPassword = MutableStateFlow<Boolean?>(null)
    val isValidPassword: StateFlow<Boolean?> = _isValidPassword.asStateFlow()

    private val _isPasswordMatch = MutableStateFlow<Boolean?>(null)
    val isPasswordMatch: StateFlow<Boolean?> = _isPasswordMatch.asStateFlow()

    private val _isNextButtonEnabled = MutableStateFlow(false)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled.asStateFlow()

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


}