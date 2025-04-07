package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Comment
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
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

    //region 사용자

    suspend fun loadInviteStatus(
        meetId: Int
    )

    //endregion


    //region 장소
    suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
    ): ActionResult<String>

    //endregion


    //region 일정
    suspend fun addSchedule(
        meetId: Int,
        userId: Int,
        date: String,
        time: String
    ): ActionResult<Schedule>

    suspend fun editSchedule(
        meetId: Int,
        userId: Int,
        date: String?,
        time: String?
    ): ActionResult<Schedule>
    //endregion

    suspend fun exitMeet(
        meetId: Int,
        userId: Int
    ): ActionResult<Unit>

    suspend fun clearMeetDetail()

}