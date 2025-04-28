package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.CommentListItem
import kotlinx.coroutines.flow.Flow

/**
 * 모임 상세화면에서 Place 상세 보기시  Comment 관련 처리
 */
interface MeetDetailCommentRepository {
    fun getCommentList(): Flow<List<CommentListItem>>

    /**
     * 해당 화면 탈출시
     */
    suspend fun clearCommentData()


    /**
     * 코멘트 데이터 로드
     */
    fun loadMeetCommentData(
        placeId: Int
    )

    /**
     * 코멘트 추가
     */
    suspend fun addComment(
        placeId: Int,
        userId: Int,
        comment: String
    ): ActionResult<*>

    /**
     * 코멘트 수정
     */
    suspend fun editComment(
        commentId: Int,
        userId: Int,
        comment: String
    ): ActionResult<*>

    /**
     * 코멘트 삭제
     */
    suspend fun deleteComment(
        commentId: Int,
        userId: Int
    ): ActionResult<*>

    /**
     * fcm 코드 301 코멘트 추가 일때 함수
     */
    suspend fun addCommentFromFcm(placeId: Int, newComment: CommentListItem)

    /**
     * fcm 코드 302 코멘트 수정 일때 함수
     */
    suspend fun updateCommentFromFcm(commentId: Int, description: String)

    /**
     * fcm 코드 303 코멘트 삭제 일때 함수
     */
    suspend fun deleteCommentFromFcm(commentId: Int)
}