package com.sooum.domain.usecase.meet.invite

import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

/**
 * 사용자가 초대장 코드를 이용해 수락한 경우
 */
class InviteFriendUseCase @Inject constructor(
    private val repository: MeetDetailRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(
        meetId: Int,
        friendId: Int
    ) = repository.invite(meetId, getLoginUserIdUseCase()!!, friendId)
}