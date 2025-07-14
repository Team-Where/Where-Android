package com.sooum.data.repository

import android.content.Context
import android.net.Uri
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.safeFlow
import com.sooum.data.network.setting.SettingApi
import com.sooum.data.network.setting.request.InquiryRequest
import com.sooum.domain.datasource.InquiryRemoteDataSource
import com.sooum.domain.datasource.NoticeRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.InquiryGetResult
import com.sooum.domain.model.InquiryWriteResult
import com.sooum.domain.model.NoticeResult
import com.sooum.domain.repository.SettingRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class SettingRepositoryImpl @Inject constructor(
    private val noticeRemoteDataSource: NoticeRemoteDataSource,
    private val inquiryRemoteDataSource: InquiryRemoteDataSource,
    private val appManageDataStore: AppManageDataStore,
    private val settingApi: SettingApi
) : SettingRepository {

    // 공지 목록을 저장하는 StateFlow
    private val _noticeList = MutableStateFlow(emptyList<NoticeResult>())

    private val noticeList
        get() = _noticeList.asStateFlow()

    //질문 목록을 저장하는 StateFlow
    private val _inquiryList = MutableStateFlow(emptyList<InquiryGetResult>())

    private val inquiryList
        get() = _inquiryList.asStateFlow()

    override fun getNoticeList(): Flow<List<NoticeResult>> = noticeList

    override fun getInquiryList(): Flow<List<InquiryGetResult>> = inquiryList

    // 공지 목록을 불러오는 함수
    override suspend fun getNotice() {
        noticeRemoteDataSource.getNotice().first().let { result ->
            if(result is ApiResult.Success){
                _noticeList.update {
                    result.data
                }
            }
        }
    }

    // 질문 답변을 불러오는 함수
    override suspend fun getInquiry() {
        inquiryRemoteDataSource.getInquiry().first().let { result ->
            if (result is ApiResult.Success) {
                _inquiryList.update {
                    result.data
                }
            }
        }
    }

    //질문답변을 보내는 함수
    override suspend fun createInquiry(
        title: String,
        content: String,
        userId: Int,
        context: Context,
        imageUrls: List<Uri>
    ): Flow<ApiResult<InquiryWriteResult>> {
        return safeFlow {

            val imageParts = imageUrls.mapNotNull { uri ->
                uriToMultipartPart(context, uri)
            }

            val inquiryRequest = InquiryRequest(userId, title, content)

            settingApi.addInquiry(inquiryRequest, imageParts)
        }
    }

    private fun uriToMultipartPart(context: Context, uri: Uri): MultipartBody.Part? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bytes = inputStream.readBytes()
            val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                "image",
                "image_${System.currentTimeMillis()}.jpg",
                requestBody
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
