package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.auth.request.NameOnlyRequest
import com.sooum.data.network.kakao.KakaoApi
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.repository.KakaoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApi,
    private val appManageDataStore: AppManageDataStore
) : KakaoRepository {

    override suspend fun kakaoLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>> {
        return safeFlow {
            val response = kakaoApi.kakaoLogin(
                authorization = accessToken,
                refreshToken = refreshToken
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val access = response.headers()["Authorization"]?.removePrefix("Bearer ")?.trim()
                    val refresh = response.headers()["Refresh-Token"]

                    access?.let { appManageDataStore.saveAccessToken(it) }
                    refresh?.let { appManageDataStore.saveRefreshToken(it) }
                }
            }

            response
        }
    }


    override suspend fun naverLogin(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>> {
        return safeFlow {
            val response = kakaoApi.naverLogin(
                authorization = accessToken,
                refreshToken = refreshToken
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val access = response.headers()["Authorization"]?.removePrefix("Bearer ")?.trim()
                    val refresh = response.headers()["Refresh-Token"]

                    access?.let { appManageDataStore.saveAccessToken(it) }
                    refresh?.let { appManageDataStore.saveRefreshToken(it) }
                }
            }

            response
        }
    }


    override suspend fun putNickName(userId: Int, nickName: String): Flow<ApiResult<Unit>> {
        val request = NameOnlyRequest(nickName)
        return safeFlow {
            kakaoApi.putNickName(
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

                kakaoApi.postProfileImage(userId, multipartBody)
            }
        } else {
            flowOf(ApiResult.Success(PostProfileResult("")))
        }
    }
}