package com.sooum.where_android.model

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object SplashRoute : ScreenRoute {

        @Serializable
        data object Splash : ScreenRoute

        @Serializable
        data object UpdateAlert : ScreenRoute

        @Serializable
        data object ErrorAlert : ScreenRoute
    }

    @Serializable
    data object AuthRoute : ScreenRoute {

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
    data object HomeRoute : ScreenRoute {

        @Serializable
        data object Main : ScreenRoute {

            @Serializable
            sealed interface BottomNavigation {

                @Serializable
                data object MeetList : BottomNavigation

                @Serializable
                data object FriendsList : BottomNavigation

                @Serializable
                data class FriendMeetDetail(
                    val friendId: Int
                ) : BottomNavigation
            }
        }

        @Serializable
        data object MeetGuide : ScreenRoute

        @Serializable
        data class MeetDetail(
            val meetDetailId: Int
        ) : ScreenRoute

        @Serializable
        data class InviteByCode(
            val name: String,
            val code: String
        ) : ScreenRoute


        @Serializable
        data object HamburgerRoute {

            @Serializable
            data object ProfileEdit : ScreenRoute

            @Serializable
            data object Notification : ScreenRoute

            @Serializable
            data object FAQ : ScreenRoute

            @Serializable
            data object Notice : ScreenRoute

            @Serializable
            data object InquiryRoute {

                @Serializable
                data object Inquiry : ScreenRoute

                @Serializable
                data object Write : ScreenRoute

            }

            @Serializable
            data object SettingRoute {

                @Serializable
                data object Setting : ScreenRoute

                @Serializable
                data object EditPassword : ScreenRoute

                @Serializable
                data object DeleteAccount : ScreenRoute
            }
        }
    }
}