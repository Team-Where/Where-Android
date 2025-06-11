package com.sooum.domain.usecase.user

import com.sooum.domain.model.User
import javax.inject.Inject

/**
 * 현재 로그인중인 user를 가져온다
 */
class GetLoginUserUseCase @Inject constructor(
    getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(): User? {
        //TODO 로그인 유저 정보 가져오기
        return User(
            1,
            "테스트유저"
        )
    }
}