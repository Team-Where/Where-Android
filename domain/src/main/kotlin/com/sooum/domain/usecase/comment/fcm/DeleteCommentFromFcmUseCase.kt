package com.sooum.domain.usecase.comment.fcm

import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import jakarta.inject.Inject

class DeleteCommentFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    suspend operator fun invoke(commentId: Int) {
        meetDetailPlaceWithCommentRepository.deleteCommentFromFcm(commentId)
    }
}