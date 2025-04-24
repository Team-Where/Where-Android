package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class TogglePlaceLikeUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) {
    suspend operator fun invoke(placeId: Int) =
        meetDetailPlaceWithCommentRepository.likeToggle(
            placeId = placeId,
            userId = getLoginUserIdUseCase()!!
        )
    
}