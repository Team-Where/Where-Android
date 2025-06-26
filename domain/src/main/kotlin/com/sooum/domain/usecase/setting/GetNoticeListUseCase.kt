package com.sooum.domain.usecase.setting

import com.sooum.domain.repository.SettingRepository
import jakarta.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    operator fun invoke() = repository.getNoticeList()
}