package com.sooum.domain.usecase.meet

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdateScheduleUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(meetId: Int, date: String, time: String){
        meetDetailRepository.updateSchedule(meetId, date, time)
    }
}