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
    ): ActionResult<String>

    suspend fun editProfile(
        userId: Int,
        imageFile: File
    ): ActionResult<String>

    suspend fun deleteProfile(
        userId: Int,
    ): ActionResult<Any>

    suspend fun editNickName(
        userId: Int,
        newNickName: String
    ): ActionResult<*>

    suspend fun deleteAccount(
        userId: Int,
    ): ActionResult<*>

    suspend fun registerToken(
        userId: Int,
        fcmToken: String
    ): ActionResult<*>

    suspend fun unRegisterToken(
        userId: Int
    ): ActionResult<*>
}