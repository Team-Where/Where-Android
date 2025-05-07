package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class KakaoSignUpResult (
    val userId: Int,
    val signUp: Boolean,
    val profileImage: String
)