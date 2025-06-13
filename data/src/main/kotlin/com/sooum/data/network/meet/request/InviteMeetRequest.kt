package com.sooum.data.network.meet.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모임 초대시 사용되는 model
 * @param[meetId] 모임 식별 id
 * @param[fromId] 초대한 사용자 id
 * @param[toId] 초대받은 사용자 id
 */
@Serializable
data class InviteMeetRequest(
    @SerialName("id")
    val meetId: Int,
    @SerialName("fromId")
    val fromId: Int,
    @SerialName("toId")
    val toId: Int
)