package com.sooum.domain.usecase.setting

import android.content.Context
import android.net.Uri
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.InquiryResult
import com.sooum.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostInquiryUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(
        userId: Int,
        title: String,
        content: String,
        imageUrls: List<Uri>,
        context: Context
    ): Flow<ApiResult<InquiryResult>> {
        return settingRepository.createInquiry(
            title = title,
            content = content,
            userId = userId,
            imageUrls = imageUrls,
            context = context
        )
    }
}