package com.sooum.data.network.place.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 장소 추가시 사용되는 model
 * @param[meetId] 모임 식별 id
 * @param[userId] 유저 식별 id
 * @param[name] 장소 이름
 * @param[address] 장소 주소
 * @param[naverLink] 네이버 지도 링크
 */
@Serializable
data class AddPlaceRequest(
    @SerialName("meetingId")
    val meetId: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("address")
    val address: String,
    @SerialName("naverLink")
    var naverLink: String? = null
)