package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.model.ActionResult
import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class ExitMeetUseCase @Inject constructor(
    val repository: MeetDetailRepository,
) {
    suspend operator fun invoke(meetId: Int, userId: Int): ActionResult<Unit> {
        return repository.exitMeet(meetId, userId)
    }
}