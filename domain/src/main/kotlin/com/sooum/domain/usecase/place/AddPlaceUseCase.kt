package com.sooum.domain.usecase.place

import com.sooum.domain.model.ShareResult
import com.sooum.domain.repository.MeetDetailPlaceRepository
import javax.inject.Inject

/**
 * 공유된 데이터로 부터
 */
class AddPlaceUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    suspend operator fun invoke(
        meetId: Int,
        userId: Int,
        shareResult: ShareResult
    ) = meetDetailPlaceWithCommentRepository.addMeetPlace(
            meetId = meetId,
            userId = userId,
            name = shareResult.placeName,
            address = shareResult.address,
        )

}