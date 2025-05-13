package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerifyResult (
    val verified: String
)