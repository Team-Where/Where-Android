package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdatePlaceLikeUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(placeId: Int, placeLikeUserList: List<Int>) {
        meetDetailRepository.updatePlaceLike(placeId, placeLikeUserList)
    }
}