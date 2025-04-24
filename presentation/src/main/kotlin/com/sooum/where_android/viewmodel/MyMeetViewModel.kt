package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.Filter
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.domain.usecase.meet.detail.LoadMeetDetailListUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MyMeetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val loadMeetDetailListUseCase: LoadMeetDetailListUseCase,
    getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    private var _filter: MutableStateFlow<Filter> = MutableStateFlow(Filter.Time)
    private val maxDate = LocalDateTime(9999, 12, 31, 23, 59, 59, 999_999_999)

    val meetDetailList = getMeetDetailListUseCase().combine(filter) { items, filter ->
        val comparator = when (filter) {
            Filter.Time -> compareBy<MeetDetail> { it.finished }
                .thenBy {
                    it.schedule?.let { schedule ->
                        LocalDateTime.parse("${schedule.date}T${schedule.time}")
                    } ?: maxDate //값이 없으면 최대 날짜로
                } // 시간 빠른 순 (오름차순)
                .thenByDescending {
                    LocalDateTime.parse(it.createdAt)
                } //이후 시간 동일 하면 최근 생성 순(내림차순)

            Filter.Create -> compareBy<MeetDetail> { it.finished }
                .thenByDescending {
                    LocalDateTime.parse(it.createdAt)
                } // 최근 생성 순 (내림차순)
                .thenBy {
                    it.schedule?.let { schedule ->
                        LocalDateTime.parse("${schedule.date}T${schedule.time}")
                    } ?: maxDate //값이 없으면 최대 날짜로
                } // 이후 생성 동일 하면 시간 빠른 순 (오름차순)
        }

        items.sortedWith(comparator)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val filter
        get() = _filter.asStateFlow()

    init {
        viewModelScope.launch {
            getLoginUserIdUseCase()?.let { id ->
                loadMeetDetailListUseCase(id)
            }
        }
    }

    fun updateFilter(filter: Filter) {
        _filter.value = filter
    }
}