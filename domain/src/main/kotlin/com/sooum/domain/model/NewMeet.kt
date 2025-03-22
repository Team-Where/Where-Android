package com.sooum.domain.model

import android.net.Uri


data class NewMeet(
    val title: String,
    val image: ImageAddType? = null,
    val participants : List<Int> = emptyList()
)

sealed class ImageAddType {
    data object Default : ImageAddType()
    data class Content(val uri: Uri) : ImageAddType()
    data class Contents(val uris: List<Uri>) : ImageAddType()
}