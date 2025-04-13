package com.sooum.domain.usecase.meet

import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class GetMeetInviteStatusUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    operator fun invoke() = repository.getMeetInviteList()
}