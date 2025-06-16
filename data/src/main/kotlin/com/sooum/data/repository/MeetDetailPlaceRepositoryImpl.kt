package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MeetDetailPlaceRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) : MeetDetailPlaceRepository {

    /**
     * [loadMeetPlaceData]이후에 해당 모임에 해당되는 장소 목록
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
     * 장소별 코멘트 처리를 위해 place를 PlaceItem으로 변환한 아이템
     */
    private val meetPlaceItemList: Flow<List<PlaceItem>> =
        _meetPlaceList.transform { placeList ->
            emit(
                placeList.map { placeWithUsers: PlaceWithUsers ->
                    val id = placeWithUsers.id
                    PlaceItem(id, placeWithUsers)
                }
            )
        }

    override fun getMeetPlaceList(): Flow<List<PlaceItem>> = meetPlaceItemList

    override fun getMeetPlaceById(placeId: Int): Flow<PlaceItem?> = meetPlaceItemList
        .map { list -> list.find { it.placeId == placeId } }

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadMeetPlaceData(meetId: Int) {
        asyncScope.launch {
            _meetPlaceList.update {
                emptyList()
            }
            val myId = getLoginUserIdUseCase()!!
            val placeListJob = async {
                meetRemoteDataSource.getMeetPlaceList(meetId, myId)
            }
            val placeListResult = placeListJob.await().first()
            if (placeListResult is ApiResult.Success) {
                _meetPlaceList.update {
                    placeListResult.data
                }
            }
        }
    }

    override suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
    ): ActionResult<Int> {
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
                    emit(ActionResult.Success(place.id))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun deleteMeetPlace(placeId: Int, userId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.deleteMeetPlace(
            placeId,
            userId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    _meetPlaceList.update { placeList ->
                        val index = placeList.indexOfFirst { it.id == placeId }
                        if (index >= 0) {
                            val tempList = placeList.toMutableList()
                            tempList.removeAt(index)
                            tempList
                        } else {
                            placeList
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

    override suspend fun updatePickStatus(placeId: Int, userId: Int): ActionResult<*> {
        return meetRemoteDataSource.pickPlace(placeId, userId).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { pickStatus ->
                    _meetPlaceList.update { placeList ->
                        val index = placeList.indexOfFirst { it.id == placeId }
                        if (index >= 0) {
                            val tempList = placeList.toMutableList()
                            val tempPlace = tempList[index].copy(
                                myLike = pickStatus.myLike,
                                likeCount = pickStatus.likeCount,
                                status = pickStatus.status
                            )
                            tempList[index] = tempPlace
                            tempList
                        } else {
                            placeList
                        }
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun updateCommentCount(placeId: Int, commentCount: Int) {
        _meetPlaceList.update { meetPlaceList ->
            val index = meetPlaceList.indexOfFirst { it.id == placeId }
            if (index >= 0) {
                val tempList = meetPlaceList.toMutableList()
                val tempItem = tempList[index].copy(comments = commentCount)
                tempList[index] = tempItem
                tempList
            } else {
                meetPlaceList
            }
        }
    }

    override suspend fun clearPlaceData() {
        _meetPlaceList.update {
            emptyList()
        }
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
}
