package com.sooum.domain.model

import android.media.Image

data class NotificationItem (
    val id: Int,
    val imageSrc : String,
    val title: String,
    val description: String,
    val receiveTime: String,
    val type: String
)