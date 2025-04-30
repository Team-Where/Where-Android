package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MeetDetailRepository {

    suspend fun loadMeetDetailList(userId: Int)

    fun getMeetDetailList(): Flow<List<MeetDetail>>

    fun getMeetInviteList(): Flow<List<MeetInviteStatus>>

    fun getMeetDetailById(meetId: Int): Flow<MeetDetail?>

    /**
     * 모임을 추가합니다.
     */
    suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ): ActionResult<NewMeetResult>

    /**
     * 현재 모임을 종료합니다.
     */
    suspend fun finishMeet(
        meetId: Int,
        userId: Int
    ): ActionResult<Unit>


    /**
     * 모임을 탕퇴합니다
     */
    suspend fun exitMeet(
        meetId: Int,
        userId: Int
    ): ActionResult<Unit>


    /**
     * 모임 제목을 업데이트 합니다.
     */
    suspend fun updateTitle(
        meetId: Int,
        userId: Int,
        title: String
    ) : ActionResult<*>

    /**
     * 모임 설명을 업데이트 합니다.
     */
    suspend fun updateDescription(
        meetId: Int,
        userId: Int,
        description: String
    ) : ActionResult<*>

    /**
     * 모임 커버를 업데이트 합니다.
     */
    suspend fun updateImage(
        meetId: Int,
        userId: Int,
        imageFile: File?
    ) : ActionResult<*>


    /**
     * 특정 id 모임을 불러온 후 불러온 상세 데이터
     */
    fun loadMeetDetailSubData(
        meetId: Int
    )


    /**
     * 일정을 추가합니다.
     */
    suspend fun addSchedule(
        meetId: Int,
        userId: Int,
        date: String,
        time: String
    ): ActionResult<Schedule>

    /**
     * 일정을 수정합니다.
     */
    suspend fun editSchedule(
        meetId: Int,
        userId: Int,
        date: String?,
        time: String?
    ): ActionResult<Schedule>


    /**
     * 해당 화면 탈출시
     */
    suspend fun clearMeetDetail()

    //fcm 관련

    /**
     * fcm 일정 변경 수신
     * - 201[com.sooum.domain.core.FcmConst.FCM_CODE_SCHEDULE_ADD] 수정
     * - 202[com.sooum.domain.core.FcmConst.FCM_CODE_SCHEDULE_PUT] 추가
     */
    suspend fun updateScheduleFromFcm(meetId: Int, date: String, time: String)

    /**
     * fcm 일정 삭제 수신
     * - 203[com.sooum.domain.core.FcmConst.FCM_CODE_SCHEDULE_DELETE] 삭제
     */
    suspend fun deleteScheduleFcm(meetId: Int)

    /**
     * fcm 모임 수락 수신
     * - 404[com.sooum.domain.core.FcmConst.FCM_CODE_MEET_ACCEPT] 수정
     */
    suspend fun updateMeetInviteStatus(userId: Int)
}