package com.sooum.domain.usecase.place

import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import javax.inject.Inject

class GetMeetPlaceListUseCase @Inject constructor(
    private val meetDetailPlaceWithCommentRepository: MeetDetailPlaceWithCommentRepository
) {
    operator fun invoke() = meetDetailPlaceWithCommentRepository.getMeetPlaceList()
}