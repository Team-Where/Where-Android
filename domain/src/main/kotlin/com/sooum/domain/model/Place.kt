package com.sooum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @param[id] Place 고유 식별 id
 * @param[meetingId] 연결된 meet id
 * @param[naverLink] 네이버 연결 링트
 * @param[kakaoLink] 카카오 연결 링크
 * @param[name] 장소 이름
 * @param[address] 장소 주소
 * @param[likeCount] 장소 좋아요 수
 * @param[status] 장소 pick 상태
 * @param[together] 같이 찾은 장소 여부
 */
@Serializable
data class Place(
    val id: Int,
    @SerialName("meetingId")
    val meetId: Int,
    val naverLink: String,
    val kakaoLink: String?,
    val name: String,
    val address: String,
    val likeCount: Int,
    @SerialName("placeStatus")
    val status: String,
    val together: Boolean
)


/**
 * @param[id] Place 고유 식별 id
 * @param[likeCount] 장소 좋아요 수
 * @param[status] 장소 pick 상태
 */
@Serializable
data class PlacePickStatus(
    @SerialName("id")
    val id: Int,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("placeStatus")
    val status: String
)


data class SelectedPlace(
    val restaurantName: String,
    val restaurantAddress: String,
    val commentCount: Int,
    val heartCount: Int
)