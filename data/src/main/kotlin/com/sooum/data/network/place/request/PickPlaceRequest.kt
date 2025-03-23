package com.sooum.data.network.place.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 장소 삭제시 사용되는 model
 * @param[placeId] 장소 식별 id
 */
@Serializable
data class PickPlaceRequest(
    @SerialName("id")
    val placeId: Int,
)