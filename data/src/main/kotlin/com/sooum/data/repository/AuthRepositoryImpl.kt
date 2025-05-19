package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.NameOnlyRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.PostProfileResult
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

    override suspend fun putNickName(userId: Int, nickName: String): Flow<ApiResult<Unit>> {
        val request = NameOnlyRequest(nickName)
        return safeFlow {
            authApi.putNickName(
                userId = userId,
                request
            )
        }
    }

    override suspend fun postProfileImage(userId: Int, imageFile: File?) : Flow<ApiResult<PostProfileResult>> {
        return if (imageFile != null){
            safeFlow {
                val requestFile = imageFile
                    .asRequestBody("image/*".toMediaTypeOrNull())

                val multipartBody = MultipartBody.Part.createFormData(
                    name = "file",
                    filename = imageFile.name,
                    body = requestFile
                )

                authApi.postProfileImage(userId, multipartBody)
            }
        } else {
            flowOf(ApiResult.Success(PostProfileResult("")))
        }
    }

}