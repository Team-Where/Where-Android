package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class MeetDetailCommentRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) : MeetDetailCommentRepository {

    private val _commentList = MutableStateFlow(emptyList<CommentListItem>())

    private val commentList
        get() = _commentList.asStateFlow()

    /**
     * [_commentList]에 정의된 값에서 뽑아온 commentId 목록
     */
    private val commentIdSet: Flow<Set<PlaceId>> =
        _commentList.transform { commentList ->
            emit(commentList.map { it.commentId }.toSet())
        }

    /**
     * 현재 focus된 placeId
     */
    private var focusPlaceId: Int = -1

    override fun getCommentList(): Flow<List<CommentListItem>> = commentList

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadMeetCommentData(placeId: Int) {
        asyncScope.launch {
            _commentList.update {
                emptyList()
            }
            val userId = getLoginUserIdUseCase()!!
            focusPlaceId = placeId
            meetRemoteDataSource.getPlaceCommentList(placeId, userId).first().let { result ->
                if (result is ApiResult.Success) {
                    _commentList.update {
                        result.data
                    }
                }
            }
        }
    }

    override suspend fun clearCommentData() {
        focusPlaceId = -1
        _commentList.value = emptyList()
    }

    override suspend fun addComment(
        placeId: Int,
        userId: Int,
        comment: String
    ): ActionResult<*> {
        return meetRemoteDataSource.addComment(
            placeId = placeId,
            userId = userId,
            description = comment
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { commentSimple ->
                    _commentList.update { commentList ->
                        val tempList = commentList.toMutableList()

                        //현재 수신된 시간 임시 설정
                        val now: String = LocalDateTime.now().toString()

                        tempList.add(
                            CommentListItem(
                                commentSimple = commentSimple,
                                placeId = placeId,
                                createdAt = now
                            )
                        )
                        tempList
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun editComment(
        commentId: Int,
        userId: Int,
        comment: String
    ): ActionResult<*> {
        return meetRemoteDataSource.editComment(
            commentId = commentId,
            userId = userId,
            description = comment
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { commentSimple ->
                    _commentList.update { commentList ->
                        val index = commentList.indexOfFirst { it.commentId == commentId }
                        if (index >= 0) {
                            val tempList = commentList.toMutableList()
                            val item = tempList[index].copy(
                                description = commentSimple.description
                            )
                            tempList[index] = item
                            tempList
                        } else {
                            commentList
                        }
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun deleteComment(commentId: Int, userId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.deleteComment(
            commentId = commentId,
            userId = userId,
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    _commentList.update { commentList ->
                        val index = commentList.indexOfFirst { it.commentId == commentId }
                        if (index >= 0) {
                            val tempList = commentList.toMutableList()
                            tempList.removeAt(index)
                            tempList
                        } else {
                            commentList
                        }
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun addCommentFromFcm(placeId: Int, newComment: CommentListItem) {
        if (focusPlaceId == placeId) {
            _commentList.update { currentList ->
                currentList + newComment
            }
        }

    }

    override suspend fun updateCommentFromFcm(commentId: Int, description: String) {
        _commentList.update { currentList ->
            currentList.map { comment ->
                if (comment.commentId == commentId) {
                    comment.copy(description = description)
                } else {
                    comment
                }
            }
        }
    }

    override suspend fun deleteCommentFromFcm(commentId: Int) {
        _commentList.update { currentList ->
            currentList.filter { it.commentId != commentId }
        }
    }
}
