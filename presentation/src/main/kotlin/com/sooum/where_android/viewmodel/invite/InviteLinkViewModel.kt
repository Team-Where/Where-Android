package com.sooum.where_android.viewmodel.invite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.usecase.meet.invite.AcceptInviteByLinkUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InviteLinkViewModel @Inject constructor(
    private val acceptInviteByLinkUseCase: AcceptInviteByLinkUseCase,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) : ViewModel() {

    fun acceptLinkCode(
        code: String
    ) {
        viewModelScope.launch {
            getLoginUserIdUseCase()?.let { userId ->
                acceptInviteByLinkUseCase(userId, code).also {
                    Log.d("JWH", it.toString())
                }
            }
        }
    }
}