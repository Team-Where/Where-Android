package com.sooum.domain.model

sealed interface Filter {
    /**
     * 시간순 필터
     */
    data object Time : Filter

    /**
     * 생성 시간순 필터
     */
    data object Create : Filter
}