package com.sooum.domain.usecase.meet.invite

import com.sooum.domain.repository.MeetDetailRepository
import javax.inject.Inject

/**
 * 사용자가 초대장 코드를 이용해 수락한 경우
 */
class AcceptInviteByLinkUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(
        userId: Int,
        link: String
    ) = repository.inviteOkFromLink(userId, link)
}