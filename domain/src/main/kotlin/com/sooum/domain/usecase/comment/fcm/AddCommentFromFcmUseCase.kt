package com.sooum.domain.usecase.comment.fcm

import com.sooum.domain.model.CommentListItem
import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import jakarta.inject.Inject

class AddCommentFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    suspend operator fun invoke(placeId: Int, newComment: CommentListItem) {
        meetDetailPlaceWithCommentRepository.addCommentFromFcm(placeId, newComment)
    }
}