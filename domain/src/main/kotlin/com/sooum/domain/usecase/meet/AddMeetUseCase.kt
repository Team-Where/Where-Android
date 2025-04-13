package com.sooum.domain.usecase.meet

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.NewMeet
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.util.UriConverter
import javax.inject.Inject

/**
 * 새로운 모임 추가시 사용되는 UseCase
 */
class AddMeetUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val uriConverter: UriConverter,
) {
    suspend operator fun invoke(
        fromId: Int,
        newMeet: NewMeet
    ) : ActionResult<NewMeetResult> {
        val file = if (newMeet.image is ImageAddType.Content) {
            uriConverter.saveContentUriToTempFile(newMeet.image.uri)
        } else {
            null
        }
        return repository.addMeet(
            title = newMeet.title,
            fromId = fromId,
            description = newMeet.description,
            participants = newMeet.participants,
            imageFile = file
        )
    }

}