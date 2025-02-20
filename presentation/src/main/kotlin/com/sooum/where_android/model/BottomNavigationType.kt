package com.sooum.where_android.model

import kotlinx.serialization.Serializable

sealed class BottomNavigationType {

    @Serializable
    data object MeetList : BottomNavigationType()

    @Serializable
    data object FriendsList : BottomNavigationType()
}
