package com.sooum.data.network.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class NameOnlyRequest(
    val nickName: String
)