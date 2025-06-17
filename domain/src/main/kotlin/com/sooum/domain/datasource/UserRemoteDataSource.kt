package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.user.MyPageInfo
import java.io.File

interface UserRemoteDataSource {

    suspend fun getMyPage(
        userId: Int
    ): ApiResult<MyPageInfo>

    suspend fun addProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String>

    suspend fun editProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String>

    suspend fun deleteProfileImage(
        userId: Int,
    ): ApiResult<*>

    suspend fun editNickname(
        userId: Int,
        nickName: String
    ): ApiResult<*>

}