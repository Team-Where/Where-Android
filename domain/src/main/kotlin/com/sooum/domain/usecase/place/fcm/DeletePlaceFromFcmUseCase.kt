package com.sooum.domain.usecase.place.fcm

import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import jakarta.inject.Inject

class DeletePlaceFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    suspend operator fun invoke(placeId: Int) {
        meetDetailPlaceWithCommentRepository.deletePlaceFromFcm(placeId)
    }
}