package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class ClearMeetUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val meetDetailPlaceWRepository: MeetDetailPlaceRepository,
    private val meetDetailCommentRepository: MeetDetailCommentRepository
) {
    suspend operator fun invoke() {
        repository.clearMeetDetail()
        meetDetailPlaceWRepository.clearPlaceData()
        meetDetailCommentRepository.clearCommentData()
    }
}