package com.sooum.where_android.model

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object SplashRoute {

        @Serializable
        data object Splash : ScreenRoute

        @Serializable
        data object UpdateAlert : ScreenRoute

        @Serializable
        data object ErrorAlert : ScreenRoute
    }

    @Serializable
    data object AuthRoute {

        @Serializable
        data object Auth : ScreenRoute

        @Serializable
        data object SocialLogin : ScreenRoute

        @Serializable
        data class SocialProfile(
            val userId: Int
        ) : ScreenRoute

        @Serializable
        data object SignIn : ScreenRoute

        @Serializable
        data object SingUpRoute {

            @Serializable
            data object Agreement : ScreenRoute

            @Serializable
            data object EmailVerification : ScreenRoute

            @Serializable
            data object Password : ScreenRoute

            @Serializable
            data object Profile : ScreenRoute

            @Serializable
            data object Complete : ScreenRoute
        }
    }

    @Serializable
    data object OnBoarding : ScreenRoute


    @Serializable
    data object Home : ScreenRoute {

        @Serializable
        sealed interface BottomNavigation {

            @Serializable
            data object MeetList : BottomNavigation

            @Serializable
            data object FriendsList : BottomNavigation
        }

        @Serializable
        data object Main : ScreenRoute

        @Serializable
        data class FriendMeetDetail(
            val friendId: Int
        ) : ScreenRoute

        @Serializable
        data object MeetGuide : ScreenRoute
    }

    @Serializable
    data class MeetDetail(
        val meetDetailId: Int
    ) : ScreenRoute

}