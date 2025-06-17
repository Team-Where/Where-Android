package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import java.io.File

interface UserRepository {

    suspend fun getMyPage(
        userId: Int
    ): ActionResult<String>

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