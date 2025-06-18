package com.sooum.where_android.model

import com.sooum.domain.model.ShareResult
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
        data class MapShareResult(
            val source: String,
            val placeName: String,
            val address: String,
            val link: String
        ) : ScreenRoute {

            constructor(shareResult: ShareResult) : this(
                shareResult.source,
                shareResult.placeName,
                shareResult.address,
                shareResult.link
            )
        }

        @Serializable
        data object HamburgerRoute {

            /**
             * 프로필 설정화면
             */
            @Serializable
            data class ProfileEdit(
                val email: String = "",
                val nickName: String = "",
                val imageSrc: String? = null
            ) : ScreenRoute

            /**
             * 알림 목록 화면
             */
            @Serializable
            data object Notification : ScreenRoute

            /**
             * FAQ 화면
             */
            @Serializable
            data object FAQ : ScreenRoute

            /**
             * 공지 화면
             */
            @Serializable
            data object Notice : ScreenRoute

            /**
             * 1대1 문의 화면
             */
            @Serializable
            data object InquiryRoute {

                /**
                 * 1대1 문의 메인
                 */
                @Serializable
                data object Inquiry : ScreenRoute

                /**
                 * 1대1 문의 작성 화면
                 */
                @Serializable
                data object Write : ScreenRoute

            }

            /**
             * 설정 화면
             */
            @Serializable
            data object SettingRoute {

                /**
                 * 설정화면 메인
                 */
                @Serializable
                data object Setting : ScreenRoute

                /**
                 * 설정화면 로그아웃
                 */
                @Serializable
                data object LogOut : ScreenRoute

                /**
                 * 설정화면 - 비번 수정
                 */
                @Serializable
                data object EditPassword : ScreenRoute

                /**
                 * 설정화면 - 계정 삭제
                 */
                @Serializable
                data object DeleteAccount : ScreenRoute

                /**
                 * 설정화면 - 계정 삭제 완료
                 */
                @Serializable
                data object DeleteComplete : ScreenRoute

            }
        }
    }
}