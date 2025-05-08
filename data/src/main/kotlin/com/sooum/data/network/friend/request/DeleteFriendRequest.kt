package com.sooum.data.network.friend.request

import kotlinx.serialization.Serializable

/**
 * 친구 삭제시 사용되는 model
 * @param[userId] 유저 식별 id
 * @param friendId 친구 구별자
 */
@Serializable
data class DeleteFriendRequest(
    val userId: Int,
    val friendId: Int,
)