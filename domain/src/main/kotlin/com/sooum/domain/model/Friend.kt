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
        val date: Date
    )

    @JvmInline
    @Serializable
    value class Date(
        val date: String //yyyy-MM-dd
    ) {
        val year: Int
            get() = date.substring(0, 4).toInt()

        val month: Int
            get() = date.substring(5, 7).toInt()

        val day: Int
            get() = date.substring(8, 10).toInt()

    }

    constructor(
        id: Int,
        name: String
    ) : this(id, name, null, true, emptyList())

    constructor(
        id: Int,
        name: String,
        favorite: Boolean
    ) : this(id, name, null, favorite, emptyList())
}

/**
 * 사용자 목록을 나타내는 축약형 모델
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