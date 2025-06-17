package com.sooum.domain.usecase

import com.sooum.domain.usecase.friend.LoadFriedListUseCase
import com.sooum.domain.usecase.meet.detail.LoadMeetDetailListUseCase
import com.sooum.domain.usecase.user.LoadUserInfoUseCase
import javax.inject.Inject

class LoadDataWhenLoginUserCase @Inject constructor(
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