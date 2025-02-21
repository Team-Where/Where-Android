package com.sooum.where_android.model

import kotlinx.serialization.Serializable

sealed class ScreenRoute {

    @Serializable
    data object Main : ScreenRoute()

    @Serializable
    data class MeetDetail(
        val detailUserId: Long
    ) : ScreenRoute()
}