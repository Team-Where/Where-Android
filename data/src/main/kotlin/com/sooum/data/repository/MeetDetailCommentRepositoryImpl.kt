package com.sooum.data.repository

import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.repository.MeetDetailCommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class MeetDetailCommentRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource
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
            focusPlaceId = placeId
            meetRemoteDataSource.getPlaceCommentList(placeId).first().let { result ->
                if (result is ApiResult.Success) {
                    _commentList.value = result.data
                }
            }
        }
    }

    override suspend fun clearCommentData() {
        focusPlaceId = -1
        _commentList.value = emptyList()
    }

    override suspend fun addCommentFromFcm(placeId: Int, newComment: CommentListItem) {
        if (focusPlaceId == placeId) {
            //현재 보고있는 화면인지 확인
            //TODO
        }
//        _commentList.update { currentList ->
//            val currentList = currentMap[placeId] ?: emptyList()
//            val updateList = currentList + newComment
//            currentMap.toMutableMap().apply {
//                this[placeId] = updateList
//            }
//        }
    }

    override suspend fun updateCommentFromFcm(commentId: Int, description: String) {
//        _commentList.update { currentList ->
//            commentMap.mapValues { (_, commentList) ->
//                if (commentList.any { it.commentId == commentId }) {
//                    commentList.map { comment ->
//                        if (comment.commentId == commentId) {
//                            comment.copy(description = description)
//                        } else {
//                            comment
//                        }
//                    }
//                } else {
//                    commentList
//                }
//            }
//        }
    }

    override suspend fun deleteCommentFromFcm(commentId: Int) {
//        _commentList.update { currentList ->
//            commentMap.mapValues { (_, commentList) ->
//                if (commentList.any { it.commentId == commentId }) {
//                    commentList.filter { it.commentId != commentId }
//                } else {
//                    commentList
//                }
//            }
//        }
    }
}
