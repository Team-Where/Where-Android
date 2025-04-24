package com.sooum.domain.usecase.place.fcm

import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import jakarta.inject.Inject

class AddNewPlaceUserFromFcmCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    suspend operator fun invoke(meetingId: Int, newPlace: PlaceWithUsers) {
        meetDetailPlaceWithCommentRepository.addPlaceFromFcm(meetingId, newPlace)
    }
}