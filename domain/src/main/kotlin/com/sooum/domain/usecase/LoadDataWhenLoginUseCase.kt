package com.sooum.domain.usecase

import com.sooum.domain.usecase.friend.LoadFriedListUseCase
import com.sooum.domain.usecase.meet.detail.LoadMeetDetailListUseCase
import com.sooum.domain.usecase.user.LoadUserInfoUseCase
import javax.inject.Inject

/**
 * 로그인시 불러올 일괄 데이터 요청
 */
class LoadDataWhenLoginUseCase @Inject constructor(
    private val loadMeetDetailListUseCase: LoadMeetDetailListUseCase,
    private val loadFriedListUseCase: LoadFriedListUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
) {
    suspend operator fun invoke(userId: Int) {
        loadMeetDetailListUseCase(userId)
        loadFriedListUseCase(userId)
        loadUserInfoUseCase(userId)
    }
}