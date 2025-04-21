package com.sooum.domain.usecase.place

import com.sooum.domain.model.CommentListItem
import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(placeId: Int, newComment: CommentListItem){
        meetDetailRepository.addComment(placeId, newComment)
    }
}