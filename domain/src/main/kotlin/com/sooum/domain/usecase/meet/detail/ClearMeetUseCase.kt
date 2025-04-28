package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class ClearMeetUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    suspend operator fun invoke() {
        repository.clearMeetDetail()
        meetDetailPlaceWithCommentRepository.clearPlaceData()
    }
}