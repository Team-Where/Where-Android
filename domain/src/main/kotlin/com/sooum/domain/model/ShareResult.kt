package com.sooum.domain.model

import kotlinx.serialization.Serializable

/**
 * 지도 앱으로 부터 공유받은 데이터
 */
@Serializable
data class ShareResult(
    val source :String,
    val placeName: String,
    val address: String,
    val link: String
)