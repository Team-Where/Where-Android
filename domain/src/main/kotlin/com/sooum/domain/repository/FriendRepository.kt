package com.sooum.domain.repository

import com.sooum.domain.model.Friend
import kotlinx.coroutines.flow.Flow

/**
 *  User관련 처리 를 가진 UserRepository 인터페이스
 */
interface FriendRepository {

    /**
     * 모든 User 목록을 가져옵니다.
     */
    fun getFriendList(): Flow<List<Friend>>

    fun loadFriend(userId: Int)

    /**
     * id에 해당하는 유저정보를 가져옵니다.
     */
    fun getFriendById(friendId: Int): Flow<Friend?>

    /**
     * 즐겨 찾기를 변경합니다.
     */
    suspend fun updateFriendFavorite(id: Int, favorite: Boolean)

    /**
     * 유저를 삭제합니다.
     */
    suspend fun deleteFriend(id: Int)
}