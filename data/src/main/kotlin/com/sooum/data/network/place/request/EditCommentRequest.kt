package com.sooum.data.network.place.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 코멘트 수정시 사용되는 model
 * @param[commentId] 코멘트 식별 Id
 * @param[userId] 유저 식별 id
 * @param[description] 작성한 코멘트 명
 */
@Serializable
data class EditCommentRequest(
    @SerialName("id")
    val commentId : Int,
    val userId :Int,
    val description :String
)