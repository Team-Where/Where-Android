package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult (
    val success: Boolean,
    val message: String
)