package com.sooum.domain.model

data class PlaceItem(
    val placeId: Int,
    val place: PlaceWithUsers,
    val commentList: List<CommentSimple>
) {
    val status
        get() = place.status

    val likeCount
        get() = place.likeCount

    val commentCount
        get() = commentList.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PlaceItem) return false

        return place == other.place &&
                commentList == other.commentList
    }

    override fun hashCode(): Int {
        var result = place.hashCode()
        result = 31 * result + commentList.hashCode()
        return result
    }
}