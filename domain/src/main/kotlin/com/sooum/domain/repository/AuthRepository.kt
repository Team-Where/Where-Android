package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.EmailVerifyResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.model.SignUpResult
import kotlinx.coroutines.flow.Flow
import java.io.File

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

}