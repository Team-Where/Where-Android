package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.UserRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getMyPage(userId: Int): ActionResult<String> {
        val result = userRemoteDataSource.getMyPage(userId)
        return result.covertApiResultToActionResultIfSuccess()
    }

    override suspend fun addProfile(userId: Int, imageFile: File): ActionResult<*> {
        val result = userRemoteDataSource.addProfileImage(userId, imageFile)
        return result.covertApiResultToActionResultIfSuccess()
    }

    override suspend fun editProfile(userId: Int, imageFile: File): ActionResult<*> {
        val result = userRemoteDataSource.editProfileImage(userId, imageFile)
        return result.covertApiResultToActionResultIfSuccess()
    }

    override suspend fun deleteProfile(userId: Int): ActionResult<*> {
        val result = userRemoteDataSource.deleteProfileImage(userId)
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }

    override suspend fun editNickName(userId: Int, nickName: String): ActionResult<*> {
        val result = userRemoteDataSource.editNickname(userId, nickName)
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }
}