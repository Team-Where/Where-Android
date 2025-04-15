package com.sooum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * @param[id] Place 고유 식별 id
 * @param[naverLink] 네이버 연결 링트
 * @param[kakaoLink] 카카오 연결 링크
 * @param[name] 장소 이름
 * @param[address] 장소 주소
 * @param[likeUserList] 장소 좋아요 유저 목록
 * @param[status] 장소 pick 상태
 * @param[together] 같이 찾은 장소 여부
 */
@Serializable
data class Place(
    val id: Int,
    val naverLink: String,
    val kakaoLink: String,
    val name: String,
    val address: String,
    @SerialName("likes")
    val likeUserList : List<Int>,
    @SerialName("placeStatus")
    val status: String,
    val together: Boolean
) {
    val likeCount :Int
        get() = likeUserList.size
}


/**
 * @param[id] Place 고유 식별 id
 * @param[like] 좋아요 여부
 * @param[status] 장소 pick 상태
 */
@Serializable
data class PlacePickStatus(
    @SerialName("id")
    val id: Int,
    @SerialName("like")
    val like: Boolean,
    @SerialName("placeStatus")
    val status: String
)


data class SelectedPlace(
    val restaurantName: String,
    val restaurantAddress: String,
    val commentCount: Int,
    val heartCount: Int
)