package com.sooum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceStatus (
    val placeId: Int,
    val placeStatus: String
)