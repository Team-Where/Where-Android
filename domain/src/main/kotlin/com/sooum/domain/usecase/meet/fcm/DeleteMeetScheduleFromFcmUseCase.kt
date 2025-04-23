package com.sooum.domain.usecase.meet.fcm

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class DeleteMeetScheduleFromFcmUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
   suspend operator fun invoke(meetId: Int) {
       meetDetailRepository.deleteScheduleFcm(meetId)
    }
}