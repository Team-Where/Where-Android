package com.sooum.where_android.viewmodel.setting

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.usecase.setting.PostInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
   private val postInquiryUseCase: PostInquiryUseCase,
   private val appManageDataStore: AppManageDataStore
)  : ViewModel(){

    fun postInquiry(
        title: String,
        content: String,
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