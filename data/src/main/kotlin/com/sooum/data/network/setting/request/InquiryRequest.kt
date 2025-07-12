package com.sooum.data.network.setting.request

import kotlinx.serialization.Serializable


@Serializable
data class InquiryRequest (
    val userId: Int,
    val title: String,
    val content: String,
    val imageUrls: List<String> = emptyList()
)