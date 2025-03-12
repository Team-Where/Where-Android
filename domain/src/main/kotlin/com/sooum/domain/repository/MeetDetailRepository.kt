package com.sooum.domain.repository

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface MeetDetailRepository {

    fun getMeetDetailList() : Flow<List<MeetDetail>>

    fun getMeetDetailById(id:Long?) : Flow<MeetDetail?>

    suspend fun updateMeetDetailSchedule(id:Long,schedule: Schedule)
}