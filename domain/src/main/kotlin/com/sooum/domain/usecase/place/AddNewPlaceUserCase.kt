package com.sooum.domain.usecase.place

import com.sooum.domain.model.Place
import com.sooum.domain.repository.MeetDetailRepository
import jakarta.inject.Inject

class AddNewPlaceUserCase @Inject constructor(
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(meetingId: Int, newPlace: Place) {
        meetDetailRepository.addPlaceToMeeting(meetingId, newPlace)
    }
}