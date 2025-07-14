package com.sooum.data.datasource

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.safeFlow
import com.sooum.data.network.setting.SettingApi
import com.sooum.domain.datasource.InquiryRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.InquiryGetResult
import com.sooum.domain.model.InquiryWriteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class InquiryRemoteDataSourceImpl @Inject constructor(
    private val settingApi: SettingApi,
    private val appManageDataStore: AppManageDataStore
) : InquiryRemoteDataSource{

    override suspend fun getInquiry(): Flow<ApiResult<List<InquiryGetResult>>> {
       return safeFlow { settingApi.getInquiry(appManageDataStore.getUserId().first()!!.toInt()) }
    }
}