package com.sooum.domain.model

sealed class PlaceList(
    open val userId: Int
) {
    data class ProfileHeader(
        override val userId: Int,
        val userName: String,
        val profileImage: String?
    ) : PlaceList(userId)

    data class PostItem(
        override val userId: Int,
        val place : Place
    ) : PlaceList(userId)
}