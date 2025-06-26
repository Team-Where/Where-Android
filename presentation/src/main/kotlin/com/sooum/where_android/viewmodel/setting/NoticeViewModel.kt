package com.sooum.where_android.viewmodel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.repository.SettingRepository
import com.sooum.domain.usecase.setting.FetchNoticeUseCase
import com.sooum.domain.usecase.setting.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
 import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class NoticeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    fetchNoticeUseCase: FetchNoticeUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
           fetchNoticeUseCase()
        }

    }

    // 공지화면 공지 목록
    val noticeList = getNoticeListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
}