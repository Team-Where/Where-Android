package com.sooum.data.network.meet.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모임 추가 요청시 사용하는 model
 * @param[meetId] 모임 식별 id
 * @param[userId] user 식별 id
 */
@Serializable
data class DeleteMeetRequest(
    @SerialName("id")
    val meetId: Int,
    @SerialName("userId")
    val userId: Int
)