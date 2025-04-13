package com.sooum.data.network.auth.request

/**
 * 이메일 체크시 사용하는 model
 * @param[email] 이메일
 */
data class CheckEmailRequest (
    val email: String
)