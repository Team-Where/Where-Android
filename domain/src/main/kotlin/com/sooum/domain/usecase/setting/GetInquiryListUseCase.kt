package com.sooum.domain.usecase.setting

import com.sooum.domain.repository.SettingRepository
import javax.inject.Inject

class GetInquiryListUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    operator fun invoke() = repository.getInquiryList()
}