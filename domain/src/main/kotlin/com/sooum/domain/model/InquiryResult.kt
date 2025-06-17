package com.sooum.domain.model

import java.text.DateFormat
import java.util.Date

data class InquiryResult (
    val id: Int,
    val title: String,
    val content: String,
    val images: String,
    val answered: Boolean,
    val answerContent: String,
    val inquiryDate: Date,
    val answerDate: Date
)