package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * 현재 장소의 Pick 상태를 갱신 합니다.
 */
class UpdatePlacePickUseCase @Inject constructor(
    private val repository: MeetDetailPlaceRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        placeId: Int
    ) = repository.updatePickStatus(
        placeId = placeId,
        userId = getLoginUserIdUseCase()!!
    )
}