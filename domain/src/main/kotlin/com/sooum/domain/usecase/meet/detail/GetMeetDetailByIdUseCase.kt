package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetDetailByIdUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    operator fun invoke(meetId: Int): Flow<MeetDetail?> {
        repository.loadMeetDetailSubData(meetId)
        meetDetailPlaceWithCommentRepository.loadMeetPlaceData(meetId)
        return repository.getMeetDetailById(meetId)
    }
}