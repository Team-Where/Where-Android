package com.sooum.domain.model

import android.media.Image
import androidx.room.Entity

@Entity(tableName = "notification")
data class NotificationItem (
    val title: String,
    val description: String,
    val receiveTime: String,
)