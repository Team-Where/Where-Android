package com.sooum.data.network.setting

import com.sooum.domain.model.NoticeResult
import retrofit2.Response
import retrofit2.http.GET

interface SettingApi {

    @GET("api/notice")
    suspend fun getNotice(): Response<List<NoticeResult>>
}