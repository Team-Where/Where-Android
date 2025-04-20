package com.sooum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val PLACE_STATE_PICK = "Picked"
const val PLACE_STATE_NOT_PICK = "NotPicked"


/**
 * @param[id] Place 고유 식별 id
 * @param[naverLink] 네이버 연결 링ㅋ,
 * @param[kakaoLink] 카카오 연결 링크
 * @param[name] 장소 이름
 * @param[address] 장소 주소
 * @param[likeCount] 장소 좋아요 수
 * @param[myLike] 장소 좋아요 여부
 * @param[status] 장소 pick 상태
 * @param together 같이 찾은 장소 여부
 */
@Serializable
data class Place(
    val id: Int,
    val naverLink: String,
    val kakaoLink: String,
    val name: String,
    val address: String,
    @SerialName("likes")
    val likeCount: Int,
    val myLike: Boolean,
    @SerialName("placeStatus")
    val status: String,
    val together: Boolean
)


/**
 * @param[id] Place 고유 식별 id
 * @param[likeCount] 좋아요 수
 * @param[myLike] 장소 좋아요 여부
 * @param[status] 장소 pick 상태
 */
@Serializable
data class PlacePickStatus(
    @SerialName("id")
    val id: Int,
    @SerialName("likes")
    val likeCount: Int,
    val myLike: Boolean,
    @SerialName("placeStatus")
    val status: String
)