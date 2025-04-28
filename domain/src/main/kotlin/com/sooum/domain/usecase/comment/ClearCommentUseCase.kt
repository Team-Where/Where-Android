package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import javax.inject.Inject

class ClearCommentUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository,
) {
    suspend operator fun invoke() {
        repository.clearCommentData()
    }
}