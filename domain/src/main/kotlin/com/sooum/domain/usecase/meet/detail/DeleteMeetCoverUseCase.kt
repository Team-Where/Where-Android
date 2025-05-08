package com.sooum.domain.usecase.meet.detail

import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

class DeleteMeetCoverUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(meetId: Int) = repository.deleteImage(
        meetId = meetId
    )
}