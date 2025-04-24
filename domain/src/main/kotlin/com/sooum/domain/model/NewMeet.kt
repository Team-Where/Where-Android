package com.sooum.domain.model

import android.net.Uri
import kotlinx.serialization.Serializable


/**
 * 새모임 추가시 저장되는 임시 데이터
 */
data class NewMeet(
    val title: String,
    val description: String,
    val image: ImageAddType? = null,
    val participants: List<Int> = emptyList()
)

/**
 * 이미지 타입
 */
sealed class ImageAddType {
    data object Default : ImageAddType()
    data class Content(val uri: Uri) : ImageAddType()
    data class Contents(val uris: List<Uri>) : ImageAddType()
}

@Serializable
data class NewMeetResult(
    val id: Int,
    val title: String,
    val image: String?
) {
    constructor(meet: Meet) : this(meet.id, meet.title, meet.image)
}
