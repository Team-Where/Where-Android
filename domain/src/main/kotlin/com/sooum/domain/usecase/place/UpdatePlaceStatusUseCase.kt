package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdatePlaceStatusUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(placeId: Int, newStatus: String) {
        meetDetailRepository.updatePlaceStatusToPicked(placeId, newStatus)
    }
}