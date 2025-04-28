package com.sooum.domain.usecase.comment.fcm

import com.sooum.domain.repository.MeetDetailCommentRepository
import jakarta.inject.Inject

class UpdateCommentFromFcmUseCase @Inject constructor(
    private val meetDetailCommentRepository: MeetDetailCommentRepository
) {
    suspend operator fun invoke(commentId: Int, description: String) {
        meetDetailCommentRepository.updateCommentFromFcm(commentId, description)
    }
}