package com.sooum.data.network.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerifyRequest (
    val email: String,
    val code: String
)