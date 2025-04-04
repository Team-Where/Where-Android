package com.sooum.domain.usecase.meet

import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class UpdateMeetScheduleUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(id: Int, schedule: Schedule) = repository.editSchedule(
        id,
        getLoginUserIdUseCase()!!,
        schedule.formatDate,
        schedule.formatTime
    )

}