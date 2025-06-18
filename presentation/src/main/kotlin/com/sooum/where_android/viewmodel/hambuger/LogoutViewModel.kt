package com.sooum.where_android.viewmodel.hambuger


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.usecase.DeleteDataWhenLogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val deleteDataWhenLogOutUseCase: DeleteDataWhenLogOutUseCase
) : ViewModel() {

    fun logout(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = deleteDataWhenLogOutUseCase()) {
                is ActionResult.Success -> {
                    onSuccess()
                }

                is ActionResult.Fail -> {
                    onFail(result.msg)
                }
            }
        }
    }
}