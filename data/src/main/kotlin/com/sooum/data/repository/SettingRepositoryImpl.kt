package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.safeFlow
import com.sooum.data.network.setting.SettingApi
import com.sooum.domain.datasource.NoticeRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.NoticeResult
import com.sooum.domain.repository.SettingRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingRepositoryImpl @Inject constructor(
    private val noticeRemoteDataSource: NoticeRemoteDataSource,
    private val appManageDataStore: AppManageDataStore
) : SettingRepository {

    private val _noticeList = MutableStateFlow(
        emptyList<NoticeResult>()
    )

    private val noticeList
        get() = _noticeList.asStateFlow()

    override fun getNoticeList(): Flow<List<NoticeResult>> = noticeList

    override suspend fun getNotice() {
        noticeRemoteDataSource.getNotice().first().let { result ->
            if(result is ApiResult.Success){
                _noticeList.update {
                    result.data
                }
            }
        }
    }


}
