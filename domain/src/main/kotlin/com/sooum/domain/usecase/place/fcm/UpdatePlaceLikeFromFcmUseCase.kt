package com.sooum.domain.usecase.place.fcm

import com.sooum.domain.repository.MeetDetailPlaceRepository
import jakarta.inject.Inject

class UpdatePlaceLikeFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    suspend operator fun invoke(placeId: Int, placeLike: Int) {
        meetDetailPlaceWithCommentRepository.updatePlaceLikeFromFcm(placeId, placeLike)
    }
}