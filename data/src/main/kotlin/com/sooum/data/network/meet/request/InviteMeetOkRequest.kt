package com.sooum.data.network.meet.request

import kotlinx.serialization.SerialName

/**
 * 모임 초대 수락 사용되는 model
 * @param[inviteId] 초대 식별 id
 */
data class InviteMeetOkRequest(
    @SerialName("id")
    val inviteId: Int,
)