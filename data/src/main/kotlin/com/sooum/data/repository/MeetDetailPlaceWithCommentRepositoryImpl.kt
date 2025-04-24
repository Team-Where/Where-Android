package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.repository.MeetDetailPlaceWithCommentRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MeetDetailPlaceWithCommentRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) : MeetDetailPlaceWithCommentRepository {

    /**
     * [loadMeetDetailSubData]이후에 해당 모임에 해당되는 장소 목록
     * 모임에 속한 장소 목록을 가져온다.
     */
    private val _meetPlaceList = MutableStateFlow(emptyList<PlaceWithUsers>())

    /**
     * [_meetPlaceList]에 정의된 값에서 뽑아온 placeId 목록
     * (해당 placeId가 있는지 검사할때 사용)
     */
    private val placeIdSet: Flow<Set<PlaceId>> =
        _meetPlaceList.transform { placeList ->
            emit(placeList.map { it.id }.toSet())
        }

    /**
     * [loadMeetDetailSubData]이후에 해당 모임에 해당되는 코멘트목록
     * 모임에 속한 PlaceId = 코멘트 리스트 로 가져온다.
     */
    private val _commentList = MutableStateFlow(emptyMap<PlaceId, List<CommentListItem>>())


    /**
     * 장소별 코멘트 처리를 위해 place와 commentList를 병합한 형태의 아이템
     */
    private val meetPlaceWithCommentList: Flow<List<PlaceItem>> =
        _meetPlaceList.combine(_commentList) { placeList, commentList ->
            return@combine placeList.map { placeWithUsers: PlaceWithUsers ->
                val id = placeWithUsers.id
                PlaceItem(
                    id,
                    placeWithUsers,
                    commentList.getOrDefault(id, emptyList()).map { CommentSimple(it) })
            }
        }

    override fun getMeetPlaceList(): Flow<List<PlaceItem>> = meetPlaceWithCommentList

    private val asyncScope = CoroutineScope(Dispatchers.IO)
    override fun loadMeetDetailSubData(meetId: Int) {
        asyncScope.launch {
            val myId = getLoginUserIdUseCase()!!
            val placeListJob = async {
                meetRemoteDataSource.getMeetPlaceList(meetId, myId)
            }
            val placeListResult = placeListJob.await().first()
            if (placeListResult is ApiResult.Success) {
                _meetPlaceList.value = placeListResult.data

                val commentTemp = mutableMapOf<Int, List<CommentListItem>>()

                _meetPlaceList.value.forEach { place ->
                    val placeId = place.id
                    meetRemoteDataSource.getPlaceCommentList(placeId).first().let { result ->
                        if (result is ApiResult.Success) {
                            commentTemp[placeId] = result.data
                        }
                    }
                }
                _commentList.value = commentTemp
            }
        }
    }

    override suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
    ): ActionResult<String> {
        return meetRemoteDataSource.addMeetPlace(
            meetId,
            userId,
            name,
            address,
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { place ->
                    val temp = _meetPlaceList.value.toMutableList()
                    temp.add(PlaceWithUsers(place))
                    _meetPlaceList.value = temp
                    emit(ActionResult.Success(place.toString()))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun likeToggle(placeId: Int, userId: Int): ActionResult<*> {
        return meetRemoteDataSource.likePlace(placeId, userId).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { pickStatus ->
                    val tempList = _meetPlaceList.value.toMutableList()
                    val index = tempList.indexOfFirst { it.id == placeId }
                    if (index >= 0) {
                        val tempPlace = tempList[index].copy(
                            myLike = pickStatus.myLike,
                            likeCount = pickStatus.likeCount,
                            status = pickStatus.status
                        )
                        tempList[index] = tempPlace
                        _meetPlaceList.value = tempList
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun clearMeetDetail() {
        _meetPlaceList.value = emptyList()
        _commentList.value = emptyMap()
    }

    override suspend fun addPlaceFromFcm(meetId: Int, newPlace: PlaceWithUsers) {
        val exists = _meetPlaceList.value.any { it.id == newPlace.id }

        if (!exists) {
            val updateList = _meetPlaceList.value.toMutableList()
            updateList.add(newPlace)
            _meetPlaceList.value = updateList
        }
    }

    override suspend fun deletePlaceFromFcm(id: Int) {
        val filteredList = _meetPlaceList.value.toMutableList().filter { it.id != id }
        _meetPlaceList.value = filteredList
    }

    override suspend fun updatePlaceStatusFromFcm(placeId: Int, newStatus: String) {
        val updatedList = _meetPlaceList.value.map { place ->
            if (place.id == placeId) {
                place.copy(status = newStatus)
            } else {
                place
            }
        }
        _meetPlaceList.value = updatedList
    }

    override suspend fun updatePlaceLikeFromFcm(placeId: Int, placeLike: Int) {
        val updatedList = _meetPlaceList.value.map { place ->
            if (place.id == placeId) {
                place.copy(likeCount = placeLike)
            } else {
                place
            }
        }
        _meetPlaceList.value = updatedList
    }

    override suspend fun addCommentFromFcm(placeId: Int, newComment: CommentListItem) {
        _commentList.update { currentMap ->
            val currentList = currentMap[placeId] ?: emptyList()
            val updateList = currentList + newComment
            currentMap.toMutableMap().apply {
                this[placeId] = updateList
            }
        }
    }

    override suspend fun updateCommentFromFcm(commentId: Int, description: String) {
        _commentList.update { commentMap ->
            commentMap.mapValues { (_, commentList) ->
                if (commentList.any { it.commentId == commentId }) {
                    commentList.map { comment ->
                        if (comment.commentId == commentId) {
                            comment.copy(description = description)
                        } else {
                            comment
                        }
                    }
                } else {
                    commentList
                }
            }
        }
    }

    override suspend fun deleteCommentFromFcm(commentId: Int) {
        _commentList.update { commentMap ->
            commentMap.mapValues { (_, commentList) ->
                if (commentList.any { it.commentId == commentId }) {
                    commentList.filter { it.commentId != commentId }
                } else {
                    commentList
                }
            }
        }
    }
}
