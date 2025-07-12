package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class InquiryGetResult (
    val id: Int,
    val title: String,
    val content: String,
    val images: List<String>,
    val answered: Boolean,
    val answerContent: String?,
    val inquiryDate: String,
    val answerDate: String,
)