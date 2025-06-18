package com.sooum.domain.usecase

import com.sooum.domain.model.ActionResult
import com.sooum.domain.provider.TokenProvider
import com.sooum.domain.repository.FriendRepository
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.repository.UserRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class DeleteDataWhenLogOutUseCase @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val meetDetailRepository: MeetDetailRepository
) {
    suspend operator fun invoke(): ActionResult<Unit> {
        val userId = getLoginUserIdUseCase()!!
        val result = userRepository.unRegisterToken(userId)
        if (result is ActionResult.Success) {
            userRepository.clearWhenLogout()
            friendRepository.clearWhenLogout()
            meetDetailRepository.clearWhenLogout()
            tokenProvider.clearAllUserData()
            return ActionResult.Success(Unit)
        }

        return ActionResult.Fail("로그아웃 실패")
    }
}