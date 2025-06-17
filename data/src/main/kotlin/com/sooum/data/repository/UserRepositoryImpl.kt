package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.UserRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.user.MyPageInfo
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    private var _myPage: MutableStateFlow<MyPageInfo?> = MutableStateFlow(null)
    private val myPage
        get() = _myPage.asStateFlow()

    override fun getMyPage(): Flow<MyPageInfo?> = myPage

    override suspend fun loadMyPage(userId: Int) {
        val result = userRemoteDataSource.getMyPage(userId)
        if (result is ApiResult.Success) {
            _myPage.update {
                result.data
            }
        } else {
            _myPage.update {
                null
            }
        }
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