package com.sooum.domain.usecase.comment.fcm

import com.sooum.domain.model.CommentListItem
import com.sooum.domain.repository.MeetDetailCommentRepository
import jakarta.inject.Inject

class AddCommentFromFcmUseCase @Inject constructor(
    private val meetDetailCommentRepository: MeetDetailCommentRepository
) {
    suspend operator fun invoke(placeId: Int, newComment: CommentListItem) {
        meetDetailCommentRepository.addCommentFromFcm(placeId, newComment)
    }
}