package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class DeletePlaceUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(placeId: Int) {
        meetDetailRepository.deletePlaceFromMeeting(placeId)
    }
}