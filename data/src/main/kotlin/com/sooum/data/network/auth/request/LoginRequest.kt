package com.sooum.data.network.auth.request

/**
 * 로그인시 사용하는 model
 * @param[email] 이메일
 * @param[password] 비밀번호
 */
data class LoginRequest (
    val email: String,
    val password: String
)