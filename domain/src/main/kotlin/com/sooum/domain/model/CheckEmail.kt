package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckEmail (
    val email: String
)