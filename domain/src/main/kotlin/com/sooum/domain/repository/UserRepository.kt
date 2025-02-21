package com.sooum.domain.repository

import com.sooum.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 *  User관련 처리 를 가진 UserRepository 인터페이스
 */
interface UserRepository {

    /**
     * 모든 User 목록을 가져옵니다.
     */
    fun getUserList(): Flow<List<User>>

    /**
     * id에 해당하는 유저정보를 가져옵니다.
     */
    fun getUserById(userId:Long) : User?

    /**
     * 즐겨 찾기를 변경합니다.
     */
    suspend fun updateUserFavorite(id: Long, favorite: Boolean)

    /**
     * 유저를 삭제합니다.
     */
    suspend fun deleteUser(id: Long)
}