package com.sooum.domain.usecase.meet

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetDetailByIdUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(meetId: Int): Flow<MeetDetail?> {
        repository.loadInviteStatus(meetId)
        return repository.getMeetDetailById(meetId)
    }
}