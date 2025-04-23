package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetDetailByIdUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    operator fun invoke(meetId: Int): Flow<MeetDetail?> {
        repository.loadMeetDetailSubData(meetId)
        meetDetailPlaceWithCommentRepository.loadMeetDetailSubData(meetId)
        return repository.getMeetDetailById(meetId)
    }
}