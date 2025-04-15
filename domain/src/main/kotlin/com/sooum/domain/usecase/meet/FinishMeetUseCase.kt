package com.sooum.domain.usecase.meet

import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class FinishMeetUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(meetId: Int) = repository.finishMeet(
        meetId = meetId,
        userId = getLoginUserIdUseCase()!!,
    )

}