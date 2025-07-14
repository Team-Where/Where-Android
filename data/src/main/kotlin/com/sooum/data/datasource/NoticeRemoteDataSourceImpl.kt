package com.sooum.data.datasource

import com.sooum.data.network.safeFlow
import com.sooum.data.network.setting.SettingApi
import com.sooum.domain.datasource.NoticeRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.NoticeResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val settingApi: SettingApi
) : NoticeRemoteDataSource {

    override suspend fun getNotice(): Flow<ApiResult<List<NoticeResult>>> {
       return safeFlow { settingApi.getNotice() }
    }
}