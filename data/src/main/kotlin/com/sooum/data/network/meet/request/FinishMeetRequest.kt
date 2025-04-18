package com.sooum.data.network.meet.request

import kotlinx.serialization.Serializable

/**
 * 모임 종료 요청시 사용하는 model
 * @param[id] 모임 식별자
 * @param[userId] 유저 식별자
 */
@Serializable
data class FinishMeetRequest(
    val id: Int,
    val userId: Int,
)