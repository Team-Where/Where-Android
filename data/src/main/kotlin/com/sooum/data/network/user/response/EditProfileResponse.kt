package com.sooum.data.network.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditProfileResponse(
    @SerialName("newLink")
    val imageSrc: String
)