package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.PostRefreshTokenResult
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
     * 버전을 체크 한다
     */
    suspend fun checkVersion(
        type: String,
        version: String
    ): Flow<ApiResult<Boolean>>

    /**
     *  이메일 인증을 요청 한다
     */
    suspend fun getEmail(
        email: String
    ): Flow<ApiResult<Unit>>

    /**
     *  이메일 인증을 검증한다
     */
    suspend fun postEmailVerify(
        email: String,
        code: String
    ): Flow<ApiResult<String>>

    /**
     *  토큰을 갱신한다
     */
    suspend fun postRefreshToken(
        refreshToken: String
    ): Flow<ApiResult<PostRefreshTokenResult>>

    /**
     *  이메일 중복을 확인한다
     */
    suspend fun checkEmail(
        email: String
    ): Flow<ApiResult<Unit>>
}