package com.sooum.domain.repository

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Comment
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
import com.sooum.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MeetDetailRepository {

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
     * [meetId]에 해당하는 초대 현황을 가져온다.
     */
    suspend fun getMeetInviteStatus(
       meetId: Int
    ): Flow<ApiResult<List<MeetInviteStatus>>>

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


    /**
     * 장소를 삭제한다.
     */
    suspend fun deleteMeetPlace(
        placeId: Int,
    ): Flow<ApiResult<Any>>

    /**
     * 장소를 선택한다.
     */
    suspend fun pickPlace(
        placeId: Int
    ): Flow<ApiResult<PlacePickStatus>>

    /**
     * 장소 좋아요 여부
     */
    suspend fun likePlace(
        placeId: Int,
        like: Boolean
    ): Flow<ApiResult<PlacePickStatus>>

    /**
     * 코멘트 추가
     */
    suspend fun addComment(
        placeId: Int,
        userId: Int,
        description: String
    ): Flow<ApiResult<Comment>>

    /**
     * 코멘트 수정
     */
    suspend fun editComment(
        commentId: Int,
        userId: Int,
        description: String
    ): Flow<ApiResult<CommentSimple>>

    /**
     * 코멘트 삭제
     */
    suspend fun deleteComment(
        commentId: Int,
        userId: Int
    ): Flow<ApiResult<Any>>

    /**
     * 코멘트 목록 가져오기
     */
    suspend fun getPlaceCommentList(
        placeId: Int
    ): Flow<ApiResult<List<CommentListItem>>>


    /**
     * 일정 추가
     */
    suspend fun addSchedule(
        meetId: Int,
        date: String,
        time: String
    ): Flow<ApiResult<Schedule>>

    /**
     * 일정 추가
     */
    suspend fun getSchedule(
        scheduleId: Int,
    ): Flow<ApiResult<Schedule>>

    /**
     * 일정 추가
     */
    suspend fun editSchedule(
        meetId: Int,
        date: String,
        time: String
    ): Flow<ApiResult<Schedule>>

    /**
     * 일정 추가
     */
    suspend fun deleteSchedule(
        meetId: Int,
    ): Flow<ApiResult<Any>>
}