package com.sooum.domain.usecase.meet

import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class ClearMeetUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke() = repository.clearMeetDetail()
}