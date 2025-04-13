package com.sooum.domain.model

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
    val image: String?,
    val link: String,
    val finished: Boolean,
    val createdAt: String,
    val schedule: Schedule?
) {
    val inviteCode: String
        get() = if (link.contains("/")) {
            link.split("/").last()
        } else {
            ""
        }

    constructor(
        id: Int,
        title: String,
        description: String,
        schedule: Schedule?
    ) : this(
        id,
        title,
        description,
        "",
        "",
        false,
        "",
        schedule
    )

    constructor(
        meet: Meet
    ) : this(
        meet.id,
        meet.title,
        meet.description,
        meet.image,
        meet.link,
        meet.finished,
        meet.createdAt,
        null
    )

    constructor(
        meet: Meet,
        schedule: Schedule?
    ) : this(
        meet.id,
        meet.title,
        meet.description,
        meet.image,
        meet.link,
        meet.finished,
        meet.createdAt,
        schedule
    )

    val year
        get() = schedule?.year

    val month
        get() = schedule?.month

    val day
        get() = schedule?.day

    val date
        get() = schedule?.date

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
    val image: String? = null,
    val finished: Boolean = false,
    val createdAt: String,
) {
    constructor(
        id: Int,
        title: String,
        description: String,
        image: String
    ) : this(id, title, description, "", image, false, "")
}


@Serializable
data class SimpleMeet(
    @SerialName("meetingId")
    val id: Int,
    val title: String,
    val image: String? = null,
    @SerialName("scheduleDate")
    val date: String? = null,
    @SerialName("scheduleTime")
    val time: String? = null
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
) {
    constructor(
        meetId: Int,
        year: Int,
        month: Int,
        day: Int,
        hour: Int
    ) : this(meetId, "$year-$month-$day", "$hour:00")

    constructor(
        meetId: Int,
        date: String,
        hour: Int
    ) : this(meetId, date, "$hour:00")

    val year = date.split("-")[0].toInt()
    val month = date.split("-")[1].toInt()
    val day = date.split("-")[2].toInt()

    val formatDate = String.format("%04d-%02d-%02d", year, month, day)

    val hour = time.split(":")[0].toInt()
    val minute = time.split(":")[1].toInt()

    val formatTime = String.format("%02d:%02d", hour, minute)
}