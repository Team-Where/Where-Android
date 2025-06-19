package com.sooum.domain.usecase.user

import com.sooum.domain.provider.TokenProvider
import javax.inject.Inject

/**
 * 현재 로그인중인 user의 id를 가져온다
 */
class GetLoginUserIdUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(): Int? {
        return tokenProvider.getUserId()
    }
}