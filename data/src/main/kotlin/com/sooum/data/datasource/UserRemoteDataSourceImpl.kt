package com.sooum.data.datasource

import com.sooum.data.network.createPart
import com.sooum.data.network.safeFlow
import com.sooum.data.network.user.UserApi
import com.sooum.data.network.user.request.EditNicknameRequest
import com.sooum.domain.datasource.UserRemoteDataSource
import com.sooum.domain.model.ApiResult
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteDataSource {

    override suspend fun getMyPage(
        userId: Int
    ): ApiResult<String> {
        return safeFlow { userApi.getMyPage(userId) }.first()
    }

    override suspend fun addProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<*> {
        return safeFlow { userApi.addProfileImage(userId, imageFile.createPart()) }.first()
    }

    override suspend fun editProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<*> {
        return safeFlow { userApi.editProfileImage(userId, imageFile.createPart()) }.first()
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
}