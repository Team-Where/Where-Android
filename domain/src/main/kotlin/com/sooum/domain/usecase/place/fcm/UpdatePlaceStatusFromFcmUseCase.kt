package com.sooum.domain.usecase.place.fcm

import com.sooum.domain.repository.MeetDetailPlaceRepository
import jakarta.inject.Inject

class UpdatePlaceStatusFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    suspend operator fun invoke(placeId: Int, newStatus: String) {
        meetDetailPlaceWithCommentRepository.updatePlaceStatusFromFcm(placeId, newStatus)
    }
}