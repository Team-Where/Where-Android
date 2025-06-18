package com.sooum.domain.usecase.user

import com.sooum.domain.model.ActionResult
import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

/**
 * [userId]에 해당하는 계정을 탈퇴 요청 합니다.
 */
class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(): ActionResult<*> {
        val userId = getLoginUserIdUseCase()!!
        return userRepository.deleteAccount(userId)
    }
}