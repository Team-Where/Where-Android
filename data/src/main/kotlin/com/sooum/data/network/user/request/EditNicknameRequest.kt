package com.sooum.data.network.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditNicknameRequest(
    @SerialName("nickName")
    val newNickname: String
)