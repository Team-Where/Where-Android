package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.NoticeResult
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getNoticeList(): Flow<List<NoticeResult>>

    /**
     * 공지사항을 불러온다.
     */
    suspend fun getNotice()
}