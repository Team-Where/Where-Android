package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class TogglePlaceLikeUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(placeId: Int, userId: Int) =
        repository.likeToggle(
            placeId = placeId,
            userId = userId
        )
    
}