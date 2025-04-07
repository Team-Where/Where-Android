package com.sooum.domain.usecase.user

import javax.inject.Inject

/**
 * 현재 로그인중인 user의 id를 가져온다
 */
class GetLoginUserIdUseCase @Inject constructor(
) {
    suspend operator fun invoke(): Int? {
        return 1
    }
}