package com.sooum.domain.usecase.place

import com.sooum.domain.model.ShareResult
import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

/**
 * 공유된 데이터로 부터
 */
class AddPlaceUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(meetId: Int, shareResult: ShareResult) {
        repository.addMeetPlace(
            meetId = meetId,
            userId = 1,
            name = shareResult.placeName,
            address =  shareResult.address,
        )
    }
}