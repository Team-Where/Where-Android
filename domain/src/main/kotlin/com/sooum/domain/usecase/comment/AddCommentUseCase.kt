package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

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