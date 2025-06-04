package com.sooum.data.repository

import android.util.Log
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.auth.request.EmailVerifyRequest
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.NameOnlyRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.EmailVerifyResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.model.PostRefreshTokenResult
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

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
       return safeFlow {
          val response =  authApi.signUp(request)
           if (response.isSuccessful) {
              val body = response.body()
              if (body != null){
                  appManageDataStore.saveUserId(body.id)
              }
           }
           response
       }
    }

    override suspend fun login(email: String, password: String): Flow<ApiResult<LoginResult>> {
        val request = LoginRequest(email, password)

        return safeFlow {
            val response = authApi.login(request)

            Log.d("AuthRepositoryImpl", response.body().toString())

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val accessToken = response.headers()["Authorization"]?.removePrefix("Bearer ")?.trim()
                    val refreshToken = response.headers()["Refresh-Token"]

                    appManageDataStore.saveUserId(body.userId)
                    accessToken?.let { appManageDataStore.saveAccessToken(it) }
                    refreshToken?.let { appManageDataStore.saveRefreshToken(it) }
                }
            }
            response
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

    override suspend fun getEmail(email: String): Flow<ApiResult<Unit>> {
        return safeFlow {
            authApi.requestEmailAuth(
                email = email
            )
        }
    }

    override suspend fun postEmailVerify(
        email: String,
        code: String
    ): Flow<ApiResult<String>> {
        val request = EmailVerifyRequest(email, code)
        return safeFlow { authApi.emailVerify(request)}

    }

    override suspend fun postRefreshToken(
        refreshToken: String
    ): Flow<ApiResult<PostRefreshTokenResult>> {
       return safeFlow {
         authApi.refreshToken(refreshToken = refreshToken)
       }
    }

}