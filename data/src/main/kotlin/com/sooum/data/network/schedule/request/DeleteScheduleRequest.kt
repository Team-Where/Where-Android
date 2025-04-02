package com.sooum.data.network.schedule.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 일정 삭제시 사용되는 model
 * @param[meetId] 모임 식별 Id
 */
@Serializable
data class DeleteScheduleRequest(
    @SerialName("id")
    val meetId: Int,
)