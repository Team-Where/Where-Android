package com.sooum.where_android.viewmodel.hambuger


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sooum.domain.model.ImageAddType
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route: ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit = savedStateHandle.toRoute()

    val email: String
        get() = route.email

    val nickName: String
        get() = route.nickName

    val imageSrc: String?
        get() = route.imageSrc

    //새로운 이미지로 변경했는지 여부
    private var updateImage: Boolean = false
    var newImageType: ImageAddType? = null
        private set

    //새로운 닉네임으로 변경했는지 여부
    private var updateNickName: Boolean = false

    var newNickName: String = ""
        private set

    private var _btnEnabled = MutableStateFlow(false)
    val btnEnabled
        get() = _btnEnabled

    fun updateImageType(imageType: ImageAddType) {
        updateImage = true
        this.newImageType = imageType
        updateBtnEnabled()
    }

    fun updateNickName(nickName: String) {
        updateNickName = true
        this.newNickName = nickName
        updateBtnEnabled()
    }

    private fun updateBtnEnabled() {
        _btnEnabled.value = if (updateImage) {
            newImageType != null
        } else {
            false
        } || if (updateNickName) {
            newNickName.isNotEmpty() && newNickName != nickName
        } else {
            false
        }
    }
}