package com.sooum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 코멘트 수신 축소형
 * @param[commentId] 코멘트 식별 Id
 * @param[description] 코멘트 내용
 */
@Serializable
data class CommentSimple(
    val commentId: Int,
    val description: String
) {
    constructor(commentListItem: CommentListItem) : this(
        commentListItem.commentId, commentListItem.description
    )
}

/**
 * 코멘트 수신 기본형
 * @param[commentId] 코멘트 식별 Id
 * @param[placeId] 장소 식별 id
 * @param[description] 코멘트 내용
 * @param createdAt 생성일자
 * @param isMine 내가 작성했는지 여부
 */
@Serializable
data class CommentListItem(
    @SerialName("id")
    val commentId: Int,
    @SerialName("placeId")
    val placeId: Int,
    val description: String,
    val createdAt: String = "",
    @SerialName("mine")
    val isMine: Boolean
) {
    constructor(
        commentSimple: CommentSimple,
        placeId: Int,
        createdAt: String,
    ) : this(
        commentId = commentSimple.commentId,
        placeId = placeId,
        description = commentSimple.description,
        createdAt = createdAt,
        isMine = true
    )
}

@Serializable
sealed class CommentData {
    data class IdOnly(val id: Int) : CommentData()
    data class Detail(val id: Int, val description: String) : CommentData()
}
