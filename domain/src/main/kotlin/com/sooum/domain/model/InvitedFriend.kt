package com.sooum.domain.model

import kotlinx.serialization.Serializable

data class InvitedFriend(
    val id : Int,
    val name: String,
    val image: String?,
)

/**
 * 모임 초대 현황 아이템
 * @param[fromId] 초대한 사람 식별 id
 * @param[fromName] 초대한 사람 이름
 * @param[toId] 초대받은 사람 식별 id
 * @param[toName] 초대받은 사람 이름
 * @param[status] 초대 여부
 * @param[toImage] 초대받은 사람 이미지
 */
@Serializable
data class MeetInviteStatus(
    val fromId: Int,
    val fromName: String,
    val toId: Int,
    val toName: String,
    val status: Boolean,
    val toImage: String?
)