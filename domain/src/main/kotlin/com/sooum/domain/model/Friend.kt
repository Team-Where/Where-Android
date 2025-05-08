package com.sooum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    @SerialName("friendId")
    val id: Int,
    @SerialName("friendName")
    val name: String,
    @SerialName("friendImage")
    val image: String?,
    @SerialName("friendBookmark")
    val isFavorite: Boolean,
    @SerialName("meetingDetail")
    val meetList: List<FriendMeet>
) {
    @Serializable
    data class FriendMeet(
        @SerialName("meetingId")
        val meetId: Int,
        @SerialName("meetingImage")
        val image: String?,
        @SerialName("meetingName")
        val name: String,
        val description: String,
        val date: String
    )
}

/**
 * 사용자 목록을 나타내는 model
 */
data class User(
    val id: Int,
    val name: String,
    val profileImage: String = "",
    val isFavorite: Boolean = false
)

fun Friend.toUser() = User(
    id = this.id,
    name = this.name,
    profileImage = this.image ?: "",
    isFavorite = isFavorite
)