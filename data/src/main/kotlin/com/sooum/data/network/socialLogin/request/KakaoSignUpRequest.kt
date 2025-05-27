package com.sooum.data.network.socialLogin.request

import kotlinx.serialization.Serializable

/**
 * 로그인시 사용하는 model
 * @param[Authorization] 카카오 어세스 토큰
 * @param[refreshToken] 카카오 리프레시 토큰
 */

@Serializable
data class KakaoSignUpRequest (
    val Authorization: String,
    val refreshToken: String
)