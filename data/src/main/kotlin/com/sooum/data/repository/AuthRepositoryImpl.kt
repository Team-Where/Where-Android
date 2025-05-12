package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val appManageDataStore: AppManageDataStore
) : AuthRepository{

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        profileImage: String
    ): Flow<ApiResult<SignUpResult>> {
        val request = SignUpRequest(
            email, password, name, profileImage
        )
       return safeFlow { authApi.signUp(request) }
    }

    override suspend fun login(email: String, password: String): Flow<ApiResult<LoginResult>> {
        val request = LoginRequest(email, password)

        return safeFlow {
            val response = authApi.login(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val accessToken = response.headers()["Authorization"]?.removePrefix("Bearer ")?.trim()
                    val refreshToken = response.headers()["Refresh-Token"]

                    accessToken?.let { appManageDataStore.saveAccessToken(it) }
                    refreshToken?.let { appManageDataStore.saveRefreshToken(it) }
                }
            }
            response
        }
    }

    override suspend fun kakaoLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>> {
        return safeFlow {
            authApi.kakaoLogin(
                authorization = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    override suspend fun checkVersion(
        type: String,
        version: String
    ): Flow<ApiResult<Boolean>> {
       return safeFlow {
           authApi.versionCheck(
               type = type,
               version = version
           )
       }
    }

}