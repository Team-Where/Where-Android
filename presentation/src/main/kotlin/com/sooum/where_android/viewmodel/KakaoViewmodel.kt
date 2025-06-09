package com.sooum.where_android.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.usecase.kakao.KakaoSignUpUseCase
import com.sooum.domain.usecase.kakao.NaverSignUpUseCase
import com.sooum.domain.usecase.kakao.NickNameUpdateUseCase
import com.sooum.domain.usecase.kakao.ProfileUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KakaoViewmodel @Inject constructor(
    private val kakaoSignUpUseCase: KakaoSignUpUseCase,
    private val nickNameUpdateUseCase: NickNameUpdateUseCase,
    private val profileUpdateUseCase: ProfileUpdateUseCase,
    private val naverSignUpUseCase: NaverSignUpUseCase,
    private val appManageDataStore: AppManageDataStore
) : ViewModel() {

    private val _kakaoSignUpState = MutableStateFlow<ApiResult<KakaoSignUpResult>>(ApiResult.SuccessEmpty)
    val kakaoSignUpState: StateFlow<ApiResult<KakaoSignUpResult>> = _kakaoSignUpState

    private val _naverSignUpState = MutableStateFlow<ApiResult<KakaoSignUpResult>>(ApiResult.SuccessEmpty)
    val naverSignUpState: StateFlow<ApiResult<KakaoSignUpResult>> = _naverSignUpState

    private val _updateNicknameState = MutableStateFlow<ApiResult<Unit>>(ApiResult.SuccessEmpty)
    val updateNicknameState: StateFlow<ApiResult<Unit>> = _updateNicknameState

    /**
     * 카카오 회원가입 기능
     */
    fun kakaoLogin(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            launch {
                appManageDataStore.saveKakaoAccessToken(accessToken)
                appManageDataStore.saveKakaoRefreshToken(refreshToken)
            }
            launch {
                kakaoSignUpUseCase(
                    accessToken, refreshToken
                ).collect { result ->
                    _kakaoSignUpState.value = result
                }
            }
        }
    }

    /**
     * 네이버 회원가입 기능
     */
    fun naverLogin(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            launch {
                appManageDataStore.saveNaverAccessToken(accessToken)
                appManageDataStore.saveKakaoRefreshToken(refreshToken)
            }
            launch {
                naverSignUpUseCase(
                    accessToken, refreshToken
                ).collect { result ->
                    _naverSignUpState.value = result
                }
            }
        }
    }

    /**
     * 닉네임 수정 기능
     */
    fun putNickName(userId: Int, nickName: String){
        viewModelScope.launch {
            nickNameUpdateUseCase(
                userId, nickName
            ).collect{ result->
                _updateNicknameState.value = result
            }
        }
    }

    /**
     * 프로필 업데이트 기능
     */
    fun updateProfile(userId: Int,imageFile: Uri){
        viewModelScope.launch {
            profileUpdateUseCase(
                userId, imageFile
            )
        }
    }
}