package com.sooum.data.network.meet.response

import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import kotlinx.serialization.Serializable


@Serializable
data class MeetListItemResponse(
    val id: Int,
    val title: String,
    val description: String,
    val link: String,
    val image: String?,
    val finished: Boolean,
    val createdAt: String,
    val scheduleDate: String?,
    val scheduleTime: String?
) {
    fun toMeetDetail(): MeetDetail {
        return MeetDetail(
            Meet(id, title, description, link, image, finished, createdAt),
            if (scheduleTime != null && scheduleDate != null) {
                Schedule(id, scheduleDate, scheduleTime)
            } else {
                null
            }
        )
    }
}