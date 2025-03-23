package com.sooum.data.network.schedule.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 일정 수정시 사용되는 model
 * @param[meetId] 모임 식별 Id
 * @param[data] 모임 날짜
 * @param[time] 모임 시간
 */
@Serializable
data class EditScheduleRequest(
    @SerialName("meetingId")
    val meetId: Int,
    val data: String? = null,
    val time: String? = null,
)