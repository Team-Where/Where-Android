package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResult (
    val email: String,
    val nickName: String,
    val profileImage: String?, // null 허용
    val existsByNickName: Boolean
)