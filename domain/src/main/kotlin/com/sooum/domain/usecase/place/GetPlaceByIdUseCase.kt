package com.sooum.domain.usecase.place

import com.sooum.domain.model.PlaceItem
import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.repository.MeetDetailPlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaceByIdUseCase @Inject constructor(
    private val repository: MeetDetailPlaceRepository,
    private val commentRepository: MeetDetailCommentRepository
) {
    operator fun invoke(placeId: Int): Flow<PlaceItem?> {
        commentRepository.loadMeetCommentData(placeId)
        return repository.getMeetPlaceById(placeId)
    }
}