package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class TogglePlaceLikeUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) {
    suspend operator fun invoke(placeId: Int) =
        meetDetailPlaceWithCommentRepository.likeToggle(
            placeId = placeId,
            userId = getLoginUserIdUseCase()!!
        )
    
}