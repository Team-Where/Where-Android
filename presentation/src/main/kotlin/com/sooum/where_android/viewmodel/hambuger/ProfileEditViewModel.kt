package com.sooum.where_android.viewmodel.hambuger


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.usecase.user.UpdateUserNickNameUseCase
import com.sooum.domain.usecase.user.UpdateUserProfileUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateUserNickNameUseCase: UpdateUserNickNameUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val route: ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit = savedStateHandle.toRoute()

    //이전 이메일 값
    val email: String
        get() = route.email

    //이전 닉네임 값
    var nickName: String = route.nickName
        private set

    //이전 프로필 값
    var imageSrc: String? = route.imageSrc

    //새로운 값 선택 여부
    var newImageType: ImageAddType? = null
        private set

    var newNickName: String = ""
        private set

    private var _btnEnabled = MutableStateFlow(false)
    val btnEnabled
        get() = _btnEnabled

    fun updateImageType(imageType: ImageAddType) {
        if (imageSrc == null && imageType == ImageAddType.Default) {
            //pass
            //현재 이미지가 없고,기본을 선택한 경우는 무시한다.
            return
        }
        this.newImageType = imageType
        updateBtnEnabled()
    }

    fun updateNickName(nickName: String) {
        this.newNickName = nickName
        updateBtnEnabled()
    }

    private val nickNameEnabled
        get() = newNickName.isNotEmpty() && newNickName != nickName


    private fun updateBtnEnabled() {
        _btnEnabled.value = nickNameEnabled || (newImageType != null)
    }

    fun updateProfileData(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            val nickNameDeferred = if (nickNameEnabled) {
                async { updateUserNickNameUseCase(newNickName) }
            } else null

            val imageDeferred = if (newImageType != null) {
                async { updateUserProfileUseCase(newImageType!!, imageSrc) }
            } else null

            val nickNameResult = nickNameDeferred?.await()
            val imageResult = imageDeferred?.await()
            Log.d("JWH-A", "nickNameResult : $nickNameResult")
            Log.d("JWH-A", "imageResult : $imageResult")
            var hasError = false
            var focusError = mutableListOf<String>()
            nickNameResult?.let {
                when (it) {
                    is ActionResult.Success -> {
                        nickName = newNickName
                        newNickName = ""
                    }

                    is ActionResult.Fail -> {
                        hasError = true
                        focusError.add("닉네임")
                    }
                }
            }

            imageResult?.let {
                when (it) {
                    is ActionResult.Success -> {
                        imageSrc = it.data
                        newImageType = null
                    }

                    is ActionResult.Fail -> {
                        hasError = true
                        focusError.add("이미지")
                    }
                }
            }

            if (!hasError) {
                onSuccess()
            } else {
                onFail(focusError.joinToString(",") + " 변경 실패")
            }
            updateBtnEnabled()
        }
    }
}