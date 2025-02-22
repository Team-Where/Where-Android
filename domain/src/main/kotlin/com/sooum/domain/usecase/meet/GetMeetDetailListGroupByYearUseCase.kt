package com.sooum.domain.usecase.meet

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetMeetDetailListGroupByYearUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke(): Flow<Map<Int, List<MeetDetail>>> {
        return repository.getMeetDetailList().transform { value: List<MeetDetail> ->
            emit(value.groupBy { it.year })
        }
    }
}