package com.sooum.data.network.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FcmRegisterRequest(
    @SerialName("fcmToken")
    val fcmToken: String?
)