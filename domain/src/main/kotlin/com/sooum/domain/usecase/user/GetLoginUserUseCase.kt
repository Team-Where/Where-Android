package com.sooum.domain.usecase.user

import com.sooum.domain.model.user.MyPageInfo
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 현재 로그인중인 user를 가져온다
 */
class GetLoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<MyPageInfo> =
        userRepository.getMyPage()
}