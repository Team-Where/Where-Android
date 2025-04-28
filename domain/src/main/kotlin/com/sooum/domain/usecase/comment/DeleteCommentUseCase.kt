package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        commentId: Int,
        comment: String
    ) = repository.deleteComment(
        commentId = commentId,
        userId = getLoginUserIdUseCase()!!,
    )
}