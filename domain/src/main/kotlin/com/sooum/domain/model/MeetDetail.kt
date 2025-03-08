package com.sooum.domain.model

import androidx.annotation.IntRange

/**
 * 모임 화면 상세 데이터
 */
data class MeetDetail(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val schedule: Schedule
) {
    val date
        get() = schedule.date

    val year
        get() = schedule.year

    val month
        get() = schedule.month

    val day
        get() = schedule.day

    constructor(
        id: Long,
        title: String,
        description: String,
        image: String,
        year: Int,
        @IntRange(from = 1, to = 12) month: Int,
        @IntRange(from = 1, to = 31) day: Int,
        @IntRange(from = 1, to = 24) time: Int
    ) : this(id, title, description, image, Schedule(year, month, day, time))

    constructor(
        id: Long,
        title: String,
        description: String,
        image: String,
        year: Int,
        @IntRange(from = 1, to = 12) month: Int,
        @IntRange(from = 1, to = 31) day: Int,
    ) : this(id, title, description, image, Schedule(year, month, day, 0))
}

/**
 * 일정관리를 위한 Schedule Class
 */
data class Schedule(
    val year: Int,
    @IntRange(from = 1, to = 12) val month: Int,
    @IntRange(from = 1, to = 31) val day: Int,
    @IntRange(from = 1, to = 24) val time: Int
) {
    val date: String
        get() {
            val st = StringBuilder()
            st.append(year)
            st.append(".")

            if (month < 10) {
                st.append(0)
                st.append(month)
            } else {
                st.append(month)
            }
            st.append(".")

            if (day < 10) {
                st.append(0)
                st.append(day)
            } else {
                st.append(day)
            }

            return st.toString()
        }
}