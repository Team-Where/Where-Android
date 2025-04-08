package com.sooum.data.network.place.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 장소 삭제시 사용되는 model
 * @param[placeId] 장소 식별 id
 * @param[userId] 좋아요 누른 유저 id
 */
@Serializable
data class LikePlaceRequest(
    @SerialName("id")
    val placeId: Int,
    @SerialName("userId")
    val userId : Int
)