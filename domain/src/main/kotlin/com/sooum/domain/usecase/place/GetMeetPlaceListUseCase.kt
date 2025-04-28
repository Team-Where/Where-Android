package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceRepository
import javax.inject.Inject

class GetMeetPlaceListUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceRepository
) {
    operator fun invoke() = meetDetailPlaceWithCommentRepository.getMeetPlaceList()
}