package com.sooum.domain.repository

import android.content.Context
import android.net.Uri
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.InquiryGetResult
import com.sooum.domain.model.InquiryWriteResult
import com.sooum.domain.model.NoticeResult
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getNoticeList(): Flow<List<NoticeResult>>

    fun getInquiryList(): Flow<List<InquiryGetResult>>

    /**
     * 공지사항을 불러온다.
     */
    suspend fun getNotice()

    /**
     * 질문답변을 불러온다.
     */
    suspend fun getInquiry()

    /**
     *  문의사항을 작성한다.
     */
    suspend fun createInquiry(
        title: String,
        content: String,
        userId: Int,
        context: Context,
        imageUrls: List<Uri> = emptyList()
    ): Flow<ApiResult<InquiryWriteResult>>

}