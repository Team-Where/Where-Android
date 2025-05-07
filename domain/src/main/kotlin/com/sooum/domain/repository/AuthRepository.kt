package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.SignUpResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * 회원가입을 한다.
     */
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        profileImage: String
    ): Flow<ApiResult<SignUpResult>>

    /**
     * 로그인을 한다
     */
    suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResult<LoginResult>>

    /**
     * 로그인을 한다
     */
    suspend fun kakaoLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>>

}