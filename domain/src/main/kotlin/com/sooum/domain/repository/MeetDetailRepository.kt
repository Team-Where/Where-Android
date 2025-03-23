package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Place
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

    /**
     * 새로운 모임을 추가한다.
     */
    suspend fun editMeet(
        id: Int,
        title: String?,
        description: String?,
        finished: Boolean?,
        imageFile: File?
    ): Flow<ApiResult<Meet>>


    /**
     * meetId에서 userId를 삭제한다.
     */
    suspend fun deleteMeet(
        meetId: Int,
        userId: Int
    ): Flow<ApiResult<Any>>

    /**
     * userId에 해당하는 모든 meet목록을 가져온다.
     */
    suspend fun getMeetList(
        userId: Int
    ): Flow<ApiResult<List<Meet>>>


    /**
     * [toUserId]에 해당하는 유저를 초대한다.
     */
    suspend fun inviteMeet(
        meetId: Int,
        fromUserId: Int,
        toUserId: Int
    ): Flow<ApiResult<Any>>


    /**
     * 장소를 추가한다.
     */
    suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
        naverLink: String?
    ): Flow<ApiResult<Place>>
}