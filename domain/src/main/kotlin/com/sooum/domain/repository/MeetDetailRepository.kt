package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MeetDetailRepository {

    fun getMeetDetailList(): Flow<List<MeetDetail>>

    fun getMeetDetailById(id: Int?): Flow<MeetDetail?>

    suspend fun updateMeetDetailSchedule(id: Int, schedule: Schedule)

    /**
     * 새로운 모임을 추가한다.
     */
    suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ): Flow<ApiResult<Meet>>
}