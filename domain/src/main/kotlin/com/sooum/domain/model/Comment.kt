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
)

/**
 * 코멘트 수신 기본형
 * @param[commentId] 코멘트 식별 Id
 * @param[placeId] 장소 식별 id
 * @param[description] 코멘트 내용
 */
@Serializable
data class CommentListItem(
    @SerialName("id")
    val commentId: Int,
    @SerialName("placeId")
    val placeId :Int,
    val description: String
)