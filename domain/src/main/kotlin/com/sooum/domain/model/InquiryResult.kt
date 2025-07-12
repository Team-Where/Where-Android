package com.sooum.domain.model

import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class InquiryResult (
    val id: Int,
    val title: String,
    val content: String,
    val images: String,
    val answered: Boolean,
    val answerContent: String,
    val inquiryDate: String,
)