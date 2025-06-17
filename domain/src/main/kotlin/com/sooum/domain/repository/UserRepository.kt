package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.user.MyPageInfo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {

    fun getMyPage(): Flow<MyPageInfo>

    suspend fun loadMyPage(
        userId: Int
    )

    suspend fun addProfile(
        userId: Int,
        imageFile: File
    ): ActionResult<*>

    suspend fun editProfile(
        userId: Int,
        imageFile: File
    ): ActionResult<*>

    suspend fun deleteProfile(
        userId: Int,
    ): ActionResult<*>

    suspend fun editNickName(
        userId: Int,
        nickName: String
    ): ActionResult<*>
}