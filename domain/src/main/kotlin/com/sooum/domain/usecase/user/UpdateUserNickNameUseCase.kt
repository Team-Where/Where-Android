package com.sooum.domain.usecase.user

import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserNickNameUseCase @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(newNickName: String) =
        userRepository.editNickName(
            userId = getLoginUserIdUseCase()!!,
            newNickName = newNickName
        )

}