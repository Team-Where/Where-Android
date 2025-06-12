package com.sooum.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SocialSignUpResult(
    val userId: Int,
    val signUp: Boolean,
    val profileImage: String?
)