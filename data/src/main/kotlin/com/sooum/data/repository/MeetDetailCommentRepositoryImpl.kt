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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MeetDetailCommentRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource
) : MeetDetailCommentRepository {

    /**
     * [loadMeetDetailSubData]이후에 해당 모임에 해당되는 코멘트목록
     * 모임에 속한 PlaceId = 코멘트 리스트 로 가져온다.
     */
    private val _commentList = MutableStateFlow(emptyList<CommentListItem>())

    private val commentList
        get() = _commentList.asStateFlow()

    override fun getCommentList(): Flow<List<CommentListItem>> = commentList

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadMeetCommentData(placeId: Int) {
        asyncScope.launch {
            meetRemoteDataSource.getPlaceCommentList(placeId).first().let { result ->
                if (result is ApiResult.Success) {
                    _commentList.value = result.data
                }
            }
        }
    }

    override suspend fun clearCommentData() {
        _commentList.value = emptyList()
    }

    override suspend fun addCommentFromFcm(placeId: Int, newComment: CommentListItem) {
        _commentList.update { currentList ->
//            val currentList = currentMap[placeId] ?: emptyList()
//            val updateList = currentList + newComment
//            currentMap.toMutableMap().apply {
//                this[placeId] = updateList
//            }
            currentList
        }
    }

    override suspend fun updateCommentFromFcm(commentId: Int, description: String) {
        _commentList.update { currentList ->
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
            currentList
        }
    }

    override suspend fun deleteCommentFromFcm(commentId: Int) {
        _commentList.update { currentList ->
//            commentMap.mapValues { (_, commentList) ->
//                if (commentList.any { it.commentId == commentId }) {
//                    commentList.filter { it.commentId != commentId }
//                } else {
//                    commentList
//                }
//            }
            currentList
        }
    }
}
