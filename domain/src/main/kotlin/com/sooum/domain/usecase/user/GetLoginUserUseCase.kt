package com.sooum.domain.usecase.user

import com.sooum.domain.model.User
import javax.inject.Inject

/**
 * 현재 로그인중인 user를 가져온다
 */
class GetLoginUserUseCase @Inject constructor(
) {
    suspend operator fun invoke(): User? {
        return User(
            1,
            "테스트유저"
        )
    }
}