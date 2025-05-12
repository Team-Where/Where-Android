package com.sooum.where_android.model

import kotlinx.serialization.Serializable

sealed class ScreenRoute {

    @Serializable
    data object MainGraph : ScreenRoute()

    sealed class BottomNavigation : ScreenRoute() {

        @Serializable
        data object MeetList : BottomNavigation()

        @Serializable
        data object FriendsList : BottomNavigation()
    }

    sealed class Home :ScreenRoute() {

        @Serializable
        data object Main : Home()

        @Serializable
        data class FriendMeetDetail(
            val friendId: Int
        ) : Home()

        @Serializable
        data object MeetGuide : Home()
    }

    @Serializable
    data class MeetDetail(
        val meetDetailId: Int
    ) : ScreenRoute()

}