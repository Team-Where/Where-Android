package com.sooum.domain.model

sealed class PlaceList {
    data class ProfileHeader(val userId: String, val userName: String, val profileImage: String) : PlaceList()
    data class PostItem(val userId: String, val title: String, val location: String) : PlaceList()
}