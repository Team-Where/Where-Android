package com.sooum.data.network.meet.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모임 초대 수락 사용되는 model(link 수락시)
 * @param userId  초대 수락하는 유저
 * @param link 초대코드
 */
@Serializable
data class InviteMeetOkLinkRequest(
    @SerialName("userId")
    val userId: Int,
    @SerialName("link")
    val link: String
)