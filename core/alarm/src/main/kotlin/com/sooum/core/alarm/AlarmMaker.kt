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
        standardData: LocalDateTime
    )

    fun cancelAlarm(meetId: Int)
}