package com.sooum.data.datasource

import com.sooum.data.network.createPart
import com.sooum.data.network.safeFlow
import com.sooum.data.network.user.UserApi
import com.sooum.data.network.user.request.EditNicknameRequest
import com.sooum.data.network.user.request.FcmRegisterRequest
import com.sooum.domain.datasource.UserRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.user.MyPageInfo
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteDataSource {

    override suspend fun getMyPage(
        userId: Int
    ): ApiResult<MyPageInfo> {
        val result = safeFlow { userApi.getMyPage(userId) }.first()
        if (result is ApiResult.Success) {
            val myPageResponse = result.data
            return ApiResult.Success(
                MyPageInfo(
                    email = myPageResponse.email,
                    nickname = myPageResponse.nickname,
                    imageSrc = myPageResponse.imageSrc,
                    existsByNickName = myPageResponse.existsByNickName
                )
            )
        } else {
            return result as ApiResult<MyPageInfo>
        }
    }

    override suspend fun addProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String> {
        val result =
            safeFlow { userApi.addProfileImage(userId, imageFile.createPart("file")) }.first()
        if (result is ApiResult.Success) {
            val addProfileResponse = result.data
            return ApiResult.Success(
                addProfileResponse.imageSrc
            )
        } else {
            return result as ApiResult<String>
        }
    }

    override suspend fun editProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String> {
        val result =
            safeFlow { userApi.editProfileImage(userId, imageFile.createPart("file")) }.first()
        if (result is ApiResult.Success) {
            val editProfileResponse = result.data
            return ApiResult.Success(
                editProfileResponse.imageSrc
            )
        } else {
            return result as ApiResult<String>
        }
    }

    override suspend fun deleteProfileImage(
        userId: Int
    ): ApiResult<*> {
        return safeFlow { userApi.deleteProfileImage(userId) }.first()
    }

    override suspend fun editNickname(userId: Int, nickName: String): ApiResult<*> {
        val request = EditNicknameRequest(
            newNickname = nickName
        )
        return safeFlow { userApi.editNickName(userId, request) }.first()
    }

    override suspend fun deleteAccount(userId: Int): ApiResult<String> {
        return safeFlow { userApi.deleteAccount(userId) }.first()
    }

    override suspend fun registerToken(userId: Int, fcmToken: String): ApiResult<String> {
        val request = FcmRegisterRequest(
            fcmToken = fcmToken
        )
        return safeFlow { userApi.registerToken(userId, request) }.first()
    }

    override suspend fun unRegisterToken(userId: Int): ApiResult<String> {
        val request = FcmRegisterRequest(
            fcmToken = null
        )
        return safeFlow { userApi.registerToken(userId, request) }.first()
    }
}