package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * [commentId]와 일치하는 코멘트를 수정합니다.
 */
class EditCommentUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        commentId: Int,
        comment: String
    ) =
        repository.editComment(
            commentId = commentId,
            userId = getLoginUserIdUseCase()!!,
            comment = comment
        )
}