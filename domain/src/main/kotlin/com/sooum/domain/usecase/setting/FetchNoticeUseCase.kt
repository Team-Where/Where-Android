package com.sooum.domain.usecase.setting

import com.sooum.domain.repository.SettingRepository
import jakarta.inject.Inject

class FetchNoticeUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke() {
        repository.getNotice()
    }
}
