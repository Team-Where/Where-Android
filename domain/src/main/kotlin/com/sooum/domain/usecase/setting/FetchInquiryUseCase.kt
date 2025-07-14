package com.sooum.domain.usecase.setting

import com.sooum.domain.repository.SettingRepository
import javax.inject.Inject

class FetchInquiryUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke() {
        repository.getInquiry()
    }
}