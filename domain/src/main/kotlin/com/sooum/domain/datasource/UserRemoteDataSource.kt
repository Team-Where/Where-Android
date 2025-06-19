package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.user.MyPageInfo
import java.io.File

interface UserRemoteDataSource {

    /**
     * 내 정보를 가져온다.
     */
    suspend fun getMyPage(
        userId: Int
    ): ApiResult<MyPageInfo>

    /**
     * 프로필 이미지를 추가한다.
     */
    suspend fun addProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String>

    /**
     * 프로필 이미지를 변경한다.
     */
    suspend fun editProfileImage(
        userId: Int,
        imageFile: File
    ): ApiResult<String>

    /**
     * 프로필 이미지를 삭제한다.
     */
    suspend fun deleteProfileImage(
        userId: Int,
    ): ApiResult<*>

    /**
     * 닉네임을 변경한다.
     */
    suspend fun editNickname(
        userId: Int,
        nickName: String
    ): ApiResult<*>

    /**
     * 계정을 삭제한다.
     */
    suspend fun deleteAccount(
        userId: Int
    ): ApiResult<String>

    /**
     * fcm 토큰을 등록한다.
     */
    suspend fun registerToken(
        userId: Int,
        fcmToken: String
    ): ApiResult<String>

    /**
     * fcm 토큰을 해제한다.
     */
    suspend fun unRegisterToken(
        userId: Int
    ): ApiResult<String>
}