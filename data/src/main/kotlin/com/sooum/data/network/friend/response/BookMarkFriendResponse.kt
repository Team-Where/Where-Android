package com.sooum.data.network.friend.response

import kotlinx.serialization.Serializable

@Serializable
data class BookMarkFriendResponse(
    val userId: Int,
    val friendId: Int,
    val bookmark: Boolean
)