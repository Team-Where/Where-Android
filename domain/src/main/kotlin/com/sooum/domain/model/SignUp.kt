package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUp(
    val email : String,
    val password: String,
    val name: String,
    val profileImage : String
)