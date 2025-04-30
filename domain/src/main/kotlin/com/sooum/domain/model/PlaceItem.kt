package com.sooum.domain.model

/**
 * 플레이스 리스트 어댑터에 사용될 아이템
 */
data class PlaceItem(
    val placeId: Int,
    val place: PlaceWithUsers,
) {
    val status
        get() = place.status

    val likeCount
        get() = place.likeCount

    val commentCount
        get() = place.comments
}