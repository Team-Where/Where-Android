package com.sooum.domain.usecase.meet

import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class UpdateMeetDetailScheduleUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(id: Int, schedule: Schedule) {

    }
}