package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdateCommentUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(commentId: Int, description: String){
        meetDetailRepository.updateComment(commentId, description)
    }
}