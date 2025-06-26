package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NoticeResult(
    val id: Int,
    val title: String,
    val content: String,
    val date: String
)