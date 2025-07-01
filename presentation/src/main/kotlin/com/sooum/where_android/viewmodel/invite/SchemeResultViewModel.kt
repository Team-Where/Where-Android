package com.sooum.where_android.viewmodel.invite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SimpleMeet
import com.sooum.domain.usecase.meet.invite.AcceptInviteByLinkUseCase
import com.sooum.domain.usecase.meet.invite.GetMeetInviteLinkUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchemeResultViewModel @Inject constructor(
    private val getMeetInviteLinkUseCase: GetMeetInviteLinkUseCase,
    private val acceptInviteByLinkUseCase: AcceptInviteByLinkUseCase,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val inviteByCode: ScreenRoute.HomeRoute.InviteByCode = savedStateHandle.toRoute()
    private var isAccepting = false

    val name = inviteByCode.name
    val code = inviteByCode.code

    fun parseSchemeData(
        onSuccess: (SimpleMeet) -> Unit,
        onFail: (msg: String) -> Unit,
    ) {
        viewModelScope.launch {
            if (code.length != 10) {
                onFail("잘못된 코드 입니다.")
            } else {
                val result = getMeetInviteLinkUseCase(code).first()
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

    fun acceptLinkCode(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        if (isAccepting) {

            return
        }
        isAccepting = true

        viewModelScope.launch {
            try {
                getLoginUserIdUseCase()?.let { userId ->
                    val result = acceptInviteByLinkUseCase(userId, code)
                    if (result is ActionResult.Success) {
                        onSuccess()
                    } else if (result is ActionResult.Fail) {
                        onFail(result.msg.ifEmpty { "초대장 수락에 실패했습니다." })
                    }
                } ?: onFail("로그인 정보가 없습니다.")
            } catch (e: Exception) {
                onFail(e.localizedMessage ?: "오류가 발생했습니다.")
            } finally {
                isAccepting = false
            }
        }
    }

}