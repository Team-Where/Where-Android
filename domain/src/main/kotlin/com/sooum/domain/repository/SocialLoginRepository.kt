package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.PostProfileResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SocialLoginRepository {

    /**
     * 카카오 로그인을 한다
     */
    suspend fun kakaoLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>>

    /**
     * 카카오 로그인을 한다
     */
    suspend fun naverLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>>

    /**
     * 닉네임을 변경한다
     */
    suspend fun putNickName(
        userId: Int,
        nickName: String
    ): Flow<ApiResult<Unit>>

    /**
     * 프로필을 엡데이트 한다
     */
    suspend fun postProfileImage(
        userId: Int,
        imageFile: File?
    ): Flow<ApiResult<PostProfileResult>>
}