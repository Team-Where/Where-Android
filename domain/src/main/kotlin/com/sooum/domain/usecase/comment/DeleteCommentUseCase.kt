package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * [commentId]에 해당 하는 코멘트를 삭제합니다.
 */
class DeleteCommentUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        commentId: Int
    ) = repository.deleteComment(
        commentId = commentId,
        userId = getLoginUserIdUseCase()!!,
    )
}