package com.sooum.data.repository

import com.sooum.domain.model.ShareResult
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl@Inject constructor(

) : PlaceRepository {

    override suspend fun addPlace(meetId: Int, shareResult: ShareResult) {

    }
}