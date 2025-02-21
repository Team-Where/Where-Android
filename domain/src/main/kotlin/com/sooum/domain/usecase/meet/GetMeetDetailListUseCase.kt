package com.sooum.domain.usecase.meet

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetDetailListUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke(): Flow<List<MeetDetail>> {
        return repository.getMeetDetailList()
    }
}