package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import java.io.File

interface UserRemoteDataSource {

    suspend fun getMyPage(
        userId: Int
    ): ApiResult<String>

    suspend fun addProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<*>

    suspend fun editProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<*>

    suspend fun deleteProfileImage(
        userId: Int,
    ): ApiResult<*>

    suspend fun editNickname(
        userId: Int,
        nickName: String
    ): ApiResult<*>

}