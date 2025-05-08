package com.sooum.core.alarm

import java.time.LocalDateTime

interface AlarmMaker {
    /**
     * 알림을 만듭니다.
     * @param meetId 모임 구분자
     * @param standardData 모임 기준 시간
     */
    fun makeAlarm(
        meetId: Int,
        meetName: String,
        standardData: LocalDateTime
    )

    /**
     * 알림을 취소합니다.
     */
    fun cancelAlarm(
        meetId: Int,
        meetName: String
    )
}