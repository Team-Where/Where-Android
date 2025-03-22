package com.sooum.domain.repository

import com.sooum.domain.model.ShareResult

interface PlaceRepository {

    suspend fun addPlace(meetId: Int, shareResult: ShareResult)
}