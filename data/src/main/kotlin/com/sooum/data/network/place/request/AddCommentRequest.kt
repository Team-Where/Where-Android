package com.sooum.data.network.place.request

import kotlinx.serialization.Serializable

/**
 * 코멘트 작성시 사용되는 model
 * @param[placeId] 모임 식별 Id
 * @param[userId] 유저 식별 id
 * @param[description] 작성한 코멘트 명
 */
@Serializable
data class AddCommentRequest(
    val placeId : Int,
    val userId :Int,
    val description :String
)