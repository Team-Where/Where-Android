package com.sooum.domain.usecase.meet

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class DeleteMeetScheduleUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
   suspend operator fun invoke(meetId: Int) {
        meetDetailRepository.deleteSchedule(meetId)
    }
}