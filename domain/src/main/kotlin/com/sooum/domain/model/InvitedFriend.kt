package com.sooum.domain.model

import kotlinx.serialization.Serializable

data class InvitedFriend (
    val image: Int,
    val name: String
)

/**
 * 모임 초대 현황 아이템
 * @param[fromId] 초대한 사람 식별 id
 * @param[fromName] 초대한 사람 이름
 * @param[toId] 초대받은 사람 식별 id
 * @param[toName] 초대받은 사람 이;름
 * @param[status] 초대 여부
 */
@Serializable
data class MeetInviteStatus(
    val fromId :Int,
    val fromName :String,
    val toId :Int,
    val toName :String,
    val status :Boolean
)