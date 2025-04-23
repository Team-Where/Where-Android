package com.sooum.domain.usecase.meet

import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class UpdateMeetDescriptionUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(id: Int, description:String) = repository.updateDescription(
        meetId = id,
        userId = getLoginUserIdUseCase()!!,
        description = description
    )

}