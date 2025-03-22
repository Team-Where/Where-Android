package com.sooum.domain.usecase.meet

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetDetailByIdUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke(id: Int?): Flow<MeetDetail?> {
        return repository.getMeetDetailById(id)
    }
}