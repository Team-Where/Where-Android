package com.sooum.data.network.auth.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

/**
 * 로그인시 사용하는 model
 * @param[username] 이메일
 * @param[password] 비밀번호
 */

@Serializable
data class LoginRequest (
    val username: String,
    val password: String
)