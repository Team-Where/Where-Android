package com.sooum.domain.usecase.user

import com.sooum.domain.model.ActionResult
import com.sooum.domain.provider.FcmTokenProvider
import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class DeleteFcmTokenUseCase @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val userRepository: UserRepository,
    private val fcmTokenProvider: FcmTokenProvider,
) {
    suspend operator fun invoke(): ActionResult<Unit> {
        val userId = getLoginUserIdUseCase()
        if (userId == null) {
            //By Pass
            return ActionResult.Fail("User Id is null")
        } else {
            val result = userRepository.unRegisterToken(userId)
            if (result is ActionResult.Success) {
                fcmTokenProvider.setSavedFcmToken(null)
                return ActionResult.Success(Unit)
            } else {
                return ActionResult.Fail("")
            }
        }
    }
}