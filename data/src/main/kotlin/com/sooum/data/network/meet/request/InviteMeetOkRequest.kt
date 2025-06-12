package com.sooum.data.network.meet.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모임 초대 수락 사용되는 model
 * @param[inviteId] 초대 식별 id
 */
@Serializable
data class InviteMeetOkRequest(
    @SerialName("id")
    val inviteId: Int,
)