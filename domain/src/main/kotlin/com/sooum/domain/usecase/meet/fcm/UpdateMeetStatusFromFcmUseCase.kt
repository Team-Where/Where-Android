package com.sooum.domain.usecase.meet.fcm

import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class UpdateMeetStatusFromFcmUseCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(userId: Int){
        meetDetailRepository.updateMeetInviteStatus(userId)
    }
}