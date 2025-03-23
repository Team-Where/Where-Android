package com.sooum.domain.usecase.meet

import com.sooum.domain.model.Meet
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetMeetDetailByIdUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke(meetId: Int?): Flow<Meet?> {
        return repository.getMeetList(1).transform {
        }
    }
}