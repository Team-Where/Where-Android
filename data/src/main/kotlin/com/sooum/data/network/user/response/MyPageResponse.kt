package com.sooum.data.network.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
    @SerialName("id")
    val userId: Int,
    @SerialName("email")
    val email: String,
    @SerialName("nickName")
    val nickname: String,
    @SerialName("profileImage")
    val imageSrc: String?,
    val existsByNickName: Boolean
)