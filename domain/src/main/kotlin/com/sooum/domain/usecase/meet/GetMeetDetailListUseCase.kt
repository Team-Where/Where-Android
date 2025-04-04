package com.sooum.domain.usecase.meet

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMeetDetailListUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke(userId: Int): Flow<List<MeetDetail>> = flow {
        val result = repository.getMeetList(userId).first { it !is ApiResult.Loading }
        val meetDetails = if (result is ApiResult.Success) {
            result.data.map { meet ->
                val scheduleResult = repository.getSchedule(meet.id).first { it !is ApiResult.Loading }
                val schedule = if (scheduleResult is ApiResult.Success) {
                    scheduleResult.data
                } else {
                    null
                }
                MeetDetail(meet, schedule)
            }
        } else {
            emptyList()
        }
        emit(meetDetails)
    }
}