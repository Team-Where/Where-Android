package com.sooum.where_android.viewmodel.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.LoadDataWhenLoginUseCase
import com.sooum.domain.usecase.kakao.KakaoSignUpUseCase
import com.sooum.domain.usecase.kakao.NaverSignUpUseCase
import com.sooum.domain.usecase.kakao.NickNameUpdateUseCase
import com.sooum.domain.usecase.kakao.ProfileUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewmodel @Inject constructor(
    private val kakaoSignUpUseCase: KakaoSignUpUseCase,
    private val naverSignUpUseCase: NaverSignUpUseCase,
    private val nickNameUpdateUseCase: NickNameUpdateUseCase,
    private val profileUpdateUseCase: ProfileUpdateUseCase,
    private val appManageDataStore: AppManageDataStore,
    private val loadDataWhenLoginUserCase: LoadDataWhenLoginUseCase
) : ViewModel() {

    /**
     * 카카오 회원가입 기능
     */
    fun kakaoLogin(
        accessToken: String,
        refreshToken: String,
        onSuccess: (isSignUp: Boolean, userId: Int) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            launch {
                appManageDataStore.saveKakaoAccessToken(accessToken)
                appManageDataStore.saveKakaoRefreshToken(refreshToken)
            }
            launch {
                kakaoSignUpUseCase(
                    accessToken, refreshToken
                ).collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val id = result.data.userId
                            async {
                                loadDataWhenLoginUserCase(id)
                            }
                            onSuccess(result.data.signUp, id)
                        }

                        is ApiResult.Fail.Error -> {
                            onFail(result.message ?: "로그인 실패")
                        }

                        else -> {
                            onFail("로그인 실패")
                        }
                    }
                }
            }
        }
    }

    /**
     * 네이버 회원가입 기능
     */
    fun naverLogin(
        accessToken: String,
        refreshToken: String,
        onSuccess: (isSignUp: Boolean, userId: Int) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            launch {
                appManageDataStore.saveNaverAccessToken(accessToken)
                appManageDataStore.saveNaverRefreshToken(refreshToken)
            }
            launch {
                naverSignUpUseCase(
                    accessToken, refreshToken
                ).collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val id = result.data.userId
                            async {
                                loadDataWhenLoginUserCase(id)
                            }
                            onSuccess(result.data.signUp, id)
                        }

                        is ApiResult.Fail.Error -> {
                            onFail(result.message ?: "로그인 실패")
                        }

                        else -> {
                            onFail("로그인 실패")
                        }
                    }
                }
            }
        }
    }

    fun putNickNameAndProfile(
        nickName: String,
        imageFile: Uri?,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            appManageDataStore.getUserId().collect { userId ->
                if (userId == null) {
                    onFail("로그인 정보를 찾을 수 없습니다.")
                    return@collect
                }

                val nickNameJob = async {
                    nickNameUpdateUseCase(
                        userId, nickName
                    )
                }

                val profileJob = async {
                    profileUpdateUseCase(
                        userId, imageFile ?: Uri.EMPTY
                    )
                }

                joinAll(nickNameJob, profileJob)

                nickNameJob.await().combine(profileJob.await()) { nickNameResult, profileResult ->
                    nickNameResult to profileResult
                }.collect { (nickNameResult, profileResult) ->
                    if (nickNameResult is ApiResult.Success && profileResult is ApiResult.Success) {
                        onSuccess()
                    } else {
                        onFail("닉네임 또는 프로필 이미지 반영 실패")
                    }
                }

                return@collect
            }
        }
    }

}