package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * [placeId]에 해당하는 장소에 코멘트를 추가합니다.
 */
class AddCommentUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        placeId: Int,
        comment: String
    ) =
        repository.addComment(
            placeId = placeId,
            userId = getLoginUserIdUseCase()!!,
            comment = comment
        )
}