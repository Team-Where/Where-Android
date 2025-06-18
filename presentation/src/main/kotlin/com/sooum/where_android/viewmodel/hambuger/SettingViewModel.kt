package com.sooum.where_android.viewmodel.hambuger


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ActionResult
import com.sooum.domain.usecase.user.DeleteFcmTokenUseCase
import com.sooum.domain.usecase.user.UpdateFcmToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appManageDataStore: AppManageDataStore,
    private val deleteFcmTokenUseCase: DeleteFcmTokenUseCase,
    private val updateFcmToken: UpdateFcmToken

) : ViewModel() {

    val notificationAllowed = appManageDataStore.getNotificationAllowed()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = true
        )

    fun updateNotificationAllowed(
        onSuccess: () -> Unit = {},
        onFail: (msg: String) -> Unit = {}
    ) {
        viewModelScope.launch {
            if (!notificationAllowed.value) {
                //등록 허용을 위해 먼저 허용으로 바꾼다.
                appManageDataStore.setNotificationAllowed(true)
                Firebase.messaging.token.addOnCompleteListener { task ->
                    viewModelScope.launch {
                        if (task.isSuccessful) {

                            val token = task.result
                            val result = updateFcmToken(token)
                            if (result is ActionResult.Success) {
                                onSuccess()
                            } else {
                                appManageDataStore.setNotificationAllowed(false)
                                onFail("Fail to Register Token")
                            }

                        } else {
                            appManageDataStore.setNotificationAllowed(false)
                            onFail("Fail to Get Token Info")
                        }
                    }
                }
            } else {
                when (val result = deleteFcmTokenUseCase()) {
                    is ActionResult.Success -> {
                        appManageDataStore.setNotificationAllowed(false)
                        onSuccess()
                    }

                    is ActionResult.Fail -> {
                        onFail(result.msg)
                    }
                }
            }
        }
    }
}