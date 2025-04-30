package com.sooum.domain.usecase.comment

import com.sooum.domain.repository.MeetDetailCommentRepository
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val repository: MeetDetailCommentRepository
) {
    operator fun invoke() = repository.getCommentList()
}