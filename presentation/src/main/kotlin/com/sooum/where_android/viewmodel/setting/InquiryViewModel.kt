package com.sooum.where_android.viewmodel.setting

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.setting.FetchInquiryUseCase
import com.sooum.domain.usecase.setting.GetInquiryListUseCase
import com.sooum.domain.usecase.setting.PostInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
   private val postInquiryUseCase: PostInquiryUseCase,
   private val appManageDataStore: AppManageDataStore,
   private val fetchInquiryUseCase: FetchInquiryUseCase,
   getInquiryListUseCase: GetInquiryListUseCase
)  : ViewModel(){

    init {
        viewModelScope.launch {
            fetchInquiryUseCase()
        }

    }

    // 문의 화면 문의 답장 목록
    private val inquiryList = getInquiryListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    // 문의 답장 된 목록
    val answeredList = inquiryList.map { it.filter { it.answered } }
    // 문의 답장 되지 않은 목록
    val unAnsweredList = inquiryList.map { it.filter { !it.answered } }


    fun postInquiry(
        title: String, content: String,
        imageUrls: List<Uri>,
        context: Context,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            postInquiryUseCase(
                userId = appManageDataStore.getUserId().first() ?: return@launch,
                title = title,
                content = content,
                imageUrls = imageUrls,
                context = context
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onSuccess()
                    }

                    is ApiResult.Fail.Error -> {
                        onFail("게시글 작성 실패")
                    }

                    else -> {
                        onFail("게시글 작성 실패")

                    }
                }
            }
        }
    }


}