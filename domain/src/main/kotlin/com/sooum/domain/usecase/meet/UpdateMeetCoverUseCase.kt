package com.sooum.domain.usecase.meet

import com.sooum.domain.model.ImageAddType
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import com.sooum.domain.util.UriConverter
import javax.inject.Inject

class UpdateMeetCoverUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val uriConverter: UriConverter
) {
    suspend operator fun invoke(id: Int, imageFile: ImageAddType) = repository.updateImage(
        meetId = id,
        userId = getLoginUserIdUseCase()!!,
        imageFile = if (imageFile is ImageAddType.Content) {
            uriConverter.saveContentUriToTempFile(imageFile.uri)
        } else {
            null
        }
    )

}