package com.sooum.domain.usecase.comment.fcm

import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import jakarta.inject.Inject

class UpdateCommentFromFcmUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    suspend operator fun invoke(commentId: Int, description: String) {
        meetDetailPlaceWithCommentRepository.updateCommentFromFcm(commentId, description)
    }
}