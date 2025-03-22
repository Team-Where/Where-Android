package com.sooum.data.network.meet.request

import kotlinx.serialization.Serializable

/**
 * 모임 추가 요청시 사용하는 model
 * @param[title] 모임명
 * @param[fromId] 초대 발송자 id
 * @param[description] 모임 설명
 * @param[participants] 초대 받는 사람 id 목록
 */
@Serializable
data class AddMeetRequest(
    val title: String,
    val fromId: Int,
    val description: String,
    val participants: List<Int> = emptyList(),
)