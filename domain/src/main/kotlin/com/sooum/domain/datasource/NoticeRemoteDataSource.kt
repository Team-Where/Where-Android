package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.NoticeResult
import kotlinx.coroutines.flow.Flow

interface NoticeRemoteDataSource {

    /**
     * 공지사항을 불러온다.
     */
    suspend fun getNotice(): Flow<ApiResult<List<NoticeResult>>>


}