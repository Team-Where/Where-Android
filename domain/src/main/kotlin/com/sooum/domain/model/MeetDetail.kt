package com.sooum.domain.model

import androidx.annotation.IntRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모임 화면 상세 데이터
 */
@Serializable
data class MeetDetail(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val schedule: Schedule
) {
    val time
        get() = schedule.time
}


/**
 * 모임 정보 수신시 활용되는 기본형 데이터
 */
@Serializable
data class Meet(
    val id: Int,
    val title: String,
    val description: String,
    val link: String,
    val image: String,
    val finished: Boolean
)

/**
 * 일정 수신시 사용되는 기본형 데이터
 * @param[meetId] 연결된 모임 id
 * @param[date] 날짜 형식 yyyy-MM-dd
 * @param[time] 시간 형식 hh:mm
 */
@Serializable
data class Schedule(
    @SerialName("meetingId")
    val meetId: Int,
    val date: String,
    val time: String
)