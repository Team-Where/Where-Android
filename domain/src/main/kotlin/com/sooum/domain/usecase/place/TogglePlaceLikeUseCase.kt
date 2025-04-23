package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class TogglePlaceLikeUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) {
    suspend operator fun invoke(placeId: Int) =
        repository.likeToggle(
            placeId = placeId,
            userId = getLoginUserIdUseCase()!!
        )
    
}