package com.sooum.data.network.meet.request

import kotlinx.serialization.Serializable

/**
 * 모임 추가 요청시 사용하는 model
 * @param[title] 모임명
 * @param[description] 모임 설명
 * @param[userId] 유저 식별자
 */
@Serializable
data class EditMeetRequest(
    val id : Int,
    var userId : Int,
    var title: String? = null,
    var description: String? = null
)