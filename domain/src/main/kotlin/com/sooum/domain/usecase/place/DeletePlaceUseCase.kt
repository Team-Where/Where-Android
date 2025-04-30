package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * 장소를 삭제합니다.
 */
class DeletePlaceUseCase @Inject constructor(
    private val repository: MeetDetailPlaceRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase

) {
    suspend operator fun invoke(
        placeId: Int,
    ) = repository.deleteMeetPlace(
        placeId = placeId,
        userId = getLoginUserIdUseCase()!!
    )
}