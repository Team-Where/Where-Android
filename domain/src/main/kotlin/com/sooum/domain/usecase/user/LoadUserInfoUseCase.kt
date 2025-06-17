package com.sooum.domain.usecase.user

import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int) {
        userRepository.loadMyPage(userId)
    }
}