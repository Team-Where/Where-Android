package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.InquiryGetResult
import com.sooum.domain.model.InquiryWriteResult
import kotlinx.coroutines.flow.Flow

interface InquiryRemoteDataSource {
    /**
     * 질문에 대한 답변을 불러온다.
     */
    suspend fun getInquiry(): Flow<ApiResult<List<InquiryGetResult>>>
}