package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(commentId: Int) {
        meetDetailRepository.deleteComment(commentId)
    }
}