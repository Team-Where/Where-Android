package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceRepository
import javax.inject.Inject

/**
 * 현재 장소의 토탈 코멘트가 변동되었음을 알립니다.(로컬)
 */
class UpdatePlaceCommentCountLocalUseCase @Inject constructor(
    private val repository: MeetDetailPlaceRepository
) {
    suspend operator fun invoke(
        placeId: Int,
        commentCount: Int
    ) = repository.updateCommentCount(
        placeId = placeId,
        commentCount = commentCount
    )
}