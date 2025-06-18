package com.sooum.where_android.viewmodel.hambuger


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.repository.TokenProvider
import com.sooum.domain.usecase.user.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    fun deleteAccount(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = deleteAccountUseCase()) {
                is ActionResult.Fail -> {
                    onFail(result.msg)
                }

                is ActionResult.Success -> {
                    tokenProvider.clearAllUserData()
                    onSuccess()
                }
            }
        }
    }
}