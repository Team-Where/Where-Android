package com.sooum.where_android.viewmodel.invite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SimpleMeet
import com.sooum.domain.usecase.meet.invite.GetMeetInviteLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchemeResultViewModel @Inject constructor(
    private val getMeetInviteLinkUseCase: GetMeetInviteLinkUseCase,
) : ViewModel() {

    fun parseSchemeData(
        name: String?,
        code: String?,
        onSuccess: (SimpleMeet) -> Unit,
        onFail: (msg: String) -> Unit,
    ) {
        viewModelScope.launch {
            if (name == null || code == null || code.length != 10) {
                onFail("")
            } else {
                val result = getMeetInviteLinkUseCase(code).first()
                Log.d("JWH", result.toString())
                when (result) {

                    is ApiResult.Success -> {
                        onSuccess(result.data)
                    }

                    is ApiResult.Fail.Error -> {
                        onFail(result.message ?: "error")
                    }

                    is ApiResult.Fail.Exception -> {
                        onFail(result.e.localizedMessage ?: "error")
                    }

                    else -> {
                        onFail("잘못된 접근 입니다.")
                    }
                }
            }
        }
    }
}