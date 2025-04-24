package com.sooum.data.network.schedule.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 일정 수정시 사용되는 model
 * @param[meetId] 모임 식별 Id
 * @param[date] 모임 날짜
 * @param[time] 모임 시간
 */
@Serializable
data class EditScheduleRequest(
    @SerialName("meetingId")
    val meetId: Int,
    @SerialName("userId")
    val userId: Int,
    val date: String? = null,
    val time: String? = null,
)