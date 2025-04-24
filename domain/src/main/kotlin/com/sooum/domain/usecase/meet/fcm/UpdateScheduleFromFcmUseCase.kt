package com.sooum.domain.usecase.meet.fcm

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdateScheduleFromFcmUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(meetId: Int, date: String, time: String){
        meetDetailRepository.updateScheduleFromFcm(meetId, date, time)
    }
}