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

    private var _myPage: MutableStateFlow<MyPageInfo> = MutableStateFlow(MyPageInfo())
    private val myPage
        get() = _myPage.asStateFlow()

    override fun getMyPage(): Flow<MyPageInfo> = myPage

    override suspend fun loadMyPage(userId: Int) {
        val result = userRemoteDataSource.getMyPage(userId)
        if (result is ApiResult.Success) {
            _myPage.update {
                result.data
            }
        } else {
            _myPage.update {
                MyPageInfo()
            }
        }
    }

    override suspend fun addProfile(userId: Int, imageFile: File): ActionResult<String> {
        val result = userRemoteDataSource.addProfileImage(userId, imageFile)
        if (result is ApiResult.Success) {
            _myPage.update { pageInfo ->
                pageInfo.copy(
                    imageSrc = result.data
                )
            }
        }
        return result.covertApiResultToActionResultIfSuccess()
    }

    override suspend fun editProfile(userId: Int, imageFile: File): ActionResult<String> {
        val result = userRemoteDataSource.editProfileImage(userId, imageFile)
        if (result is ApiResult.Success) {
            _myPage.update { pageInfo ->
                pageInfo.copy(
                    imageSrc = result.data
                )
            }
        }
        return result.covertApiResultToActionResultIfSuccess()
    }

    override suspend fun deleteProfile(userId: Int): ActionResult<Any> {
        val result = userRemoteDataSource.deleteProfileImage(userId)
        if (result is ApiResult.SuccessEmpty) {
            _myPage.update { pageInfo ->
                pageInfo.copy(
                    imageSrc = null
                )
            }
        }
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }

    override suspend fun editNickName(userId: Int, newNickName: String): ActionResult<*> {
        val result = userRemoteDataSource.editNickname(userId, newNickName)
        if (result is ApiResult.SuccessEmpty) {
            _myPage.update { pageInfo ->
                pageInfo.copy(
                    nickname = newNickName
                )
            }
        }
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }

    override suspend fun deleteAccount(userId: Int): ActionResult<*> {
        val result = userRemoteDataSource.deleteAccount(userId)
        if (result is ApiResult.SuccessEmpty) {
            _myPage.update {
                MyPageInfo()
            }
        }
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }

    override suspend fun registerToken(userId: Int, fcmToken: String): ActionResult<*> {
        val result = userRemoteDataSource.registerToken(userId, fcmToken)
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }

    override suspend fun unRegisterToken(userId: Int): ActionResult<*> {
        val result = userRemoteDataSource.unRegisterToken(userId)
        return result.covertApiResultToActionResultIfSuccessEmpty()
    }
}