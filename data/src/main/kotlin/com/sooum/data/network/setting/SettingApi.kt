package com.sooum.data.network.setting

import com.sooum.data.network.setting.request.InquiryRequest
import com.sooum.domain.model.InquiryGetResult
import com.sooum.domain.model.InquiryWriteResult
import com.sooum.domain.model.NoticeResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SettingApi {

    @GET("api/notice")
    suspend fun getNotice(): Response<List<NoticeResult>>

    @Multipart
    @POST("api/inquiry")
    suspend fun addInquiry(
        @Part("data") data: InquiryRequest,
        @Part images: List<MultipartBody.Part>?
    ): Response<InquiryWriteResult>

    @GET("api/inquiry/{id}")
    suspend fun getInquiry(
        @Path("id") id: Int
    ): Response<List<InquiryGetResult>>
}