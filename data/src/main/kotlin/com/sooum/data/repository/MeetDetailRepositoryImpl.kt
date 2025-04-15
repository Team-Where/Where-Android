package com.sooum.data.repository

import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Place
import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

typealias UserId = Int
typealias PlaceId = Int

class MeetDetailRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
) : MeetDetailRepository {

    /**
     * 로그인된 유저의 id로 가져온 전체 모임 목록
     */
    private val _meetDetailList = MutableStateFlow(emptyList<MeetDetail>())

    /**
     * [loadMeetDetailSubData]이후 해당 모임에 해당되는 초대 현황 리스트
     */
    private val _meetInviteStatus = MutableStateFlow(emptyList<MeetInviteStatus>())

    /**
     * [loadMeetDetailSubData]이후에 해당 모임에 해당되는 장소 목록
     * 모임에 속한 userId = 장소 목록 형태로 가져온다.
     */
    private val _meetPlaceList = MutableStateFlow(emptyMap<UserId, List<Place>>())

    /**
     * [_meetPlaceList]에 정의된 값에서 뽑아온 placeId 목록
     * (해당 placeId가 있는지 검사할때 사용)
     */
    private val _meetPlaceIdSet = _meetPlaceList.transform {placeMap ->
        emit(placeMap.values.flatten().map { it.id })
    }

    /**
     * [loadMeetDetailSubData]이후에 해당 모임에 해당되는 코멘트목록
     * 모임에 속한 PlaceId = 코멘트 리스트 로 가져온다.
     */
    private val _commentList = MutableStateFlow(emptyMap<PlaceId, List<CommentListItem>>())

    private val meetDetailList
        get() = _meetDetailList.asStateFlow()

    private val meetInviteStatus
        get() = _meetInviteStatus.asStateFlow()

    private val meetPlaceList
        get() = _meetPlaceList.asStateFlow()

    private val commentList
        get() = _commentList.asStateFlow()


    override suspend fun loadMeetDetailList(userId: UserId) {
        val meetDetails = meetRemoteDataSource.getMeetList(userId).first()
        _meetDetailList.value = meetDetails
    }

    override fun getMeetDetailList(): Flow<List<MeetDetail>> = meetDetailList

    override fun getMeetInviteList(): Flow<List<MeetInviteStatus>> = meetInviteStatus

    override fun getMeetPlaceList(): Flow<Map<Int, List<Place>>> = meetPlaceList

    override fun getMeetDetailById(id: Int): Flow<MeetDetail?> = meetDetailList
        .map { list -> list.find { it.id == id } }

    override suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ): ActionResult<NewMeetResult> {
        return meetRemoteDataSource.addMeet(
            title,
            fromId,
            description,
            participants,
            imageFile
        ).transform { result ->
            when (result) {
                is ApiResult.SuccessEmpty -> {

                }

                is ApiResult.Success -> {
                    val newMeetData = result.data
                    val temp = _meetDetailList.value.toMutableList()
                    temp.add(MeetDetail(newMeetData))
                    _meetDetailList.value = temp
                    emit(ActionResult.Success(NewMeetResult(newMeetData)))
                }

                is ApiResult.Fail.Error -> {
                    emit(ActionResult.Fail(result.message ?: "unknown error "))
                }

                is ApiResult.Fail.Exception -> {
                    emit(ActionResult.Fail(result.e.message ?: "unknown error"))
                }
            }
        }.first()
    }

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadMeetDetailSubData(meetId: Int) {
        asyncScope.launch {
            val myId = getLoginUserIdUseCase()

            meetRemoteDataSource.getMeetInviteStatus(
                meetId
            ).first().let { result ->
                if (result is ApiResult.Success) {
                    _meetInviteStatus.value = result.data
                }

                val temp = mutableMapOf<Int, List<Place>>()

                myId?.let { id ->
                    meetRemoteDataSource.getMeetPlaceList(meetId, id).first().let { result ->
                        if (result is ApiResult.Success) {
                            temp[id] = result.data
                        }
                    }
                }

                _meetInviteStatus.value.filter { it.status }.forEach {
                    val userId = it.toId
                    meetRemoteDataSource.getMeetPlaceList(meetId, userId).first().let { result ->
                        if (result is ApiResult.Success) {
                            temp[userId] = result.data
                        }
                    }
                }
                val commentTemp = mutableMapOf<Int, List<CommentListItem>>()
                temp.values.flatten().forEach { place ->
                    val placeId = place.id
                    meetRemoteDataSource.getPlaceCommentList(placeId).first().let { result ->
                        if (result is ApiResult.Success) {
                            commentTemp[placeId] = result.data
                        }
                    }
                }
                _meetPlaceList.value = temp
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
            if (result is ApiResult.Success) {
                val place = result.data
                val temp = _meetPlaceList.value.toMutableMap()
                if (temp.contains(userId)) {
                    val tempList = temp[userId]!!.toMutableList()
                    tempList.add(place)
                    temp[userId] = tempList
                } else {
                    temp[userId] = listOf(place)
                }
                _meetPlaceList.value = temp
                emit(ActionResult.Success(result.data.toString()))
            } else {
                emit(ActionResult.Fail(""))
            }
        }.first()
    }

    override suspend fun addSchedule(
        meetId: Int,
        userId: Int,
        date: String,
        time: String
    ): ActionResult<Schedule> {
        return meetRemoteDataSource.addSchedule(
            meetId,
            userId,
            date,
            time,
        ).transform { result ->
            if (result is ApiResult.Success) {
                val schedule = result.data
                val tempList = _meetDetailList.value.toMutableList()
                val index = tempList.indexOfFirst { it.id == meetId }
                val item = tempList[index].copy(
                    schedule = schedule
                )
                tempList[index] = item
                _meetDetailList.value = tempList
                emit(ActionResult.Success(result.data))
            } else {
                emit(ActionResult.Fail(""))
            }
        }.first()
    }

    override suspend fun editSchedule(
        meetId: Int,
        userId: Int,
        date: String?,
        time: String?
    ): ActionResult<Schedule> {
        return meetRemoteDataSource.editSchedule(
            meetId,
            userId,
            date,
            time,
        ).transform { result ->
            if (result is ApiResult.Success) {
                val schedule = result.data
                val tempList = _meetDetailList.value.toMutableList()
                val index = tempList.indexOfFirst { it.id == meetId }
                val item = tempList[index].copy(
                    schedule = schedule
                )
                tempList[index] = item
                _meetDetailList.value = tempList
                emit(ActionResult.Success(result.data))
            } else {
                emit(ActionResult.Fail(""))
            }
        }.first()
    }

    override suspend fun likeToggle(placeId: Int, userId: Int) : ActionResult<Unit> {
        val result = meetRemoteDataSource.likePlace(placeId, userId).first()
        if (result is ApiResult.Success) {
            val pickStatus = result.data
            val temp = _meetPlaceList.value.toMutableMap()
            val userPlaceList = temp[userId]?.toMutableList()
            if (userPlaceList != null) {
                val placeItemIndex = userPlaceList.indexOfFirst { it.id == placeId }
                if (placeItemIndex >= 0) {
                    val tempLikedList = userPlaceList[placeItemIndex].likeUserList.toMutableList()
                    if (pickStatus.like) {
                        tempLikedList.add(userId)
                    } else {
                        tempLikedList.removeIf {
                            it == userId
                        }
                    }
                    val newPlaceItem = userPlaceList[placeItemIndex].copy(likeUserList = tempLikedList)
                    userPlaceList[placeItemIndex] = newPlaceItem
                    temp[userId] = userPlaceList
                    _meetPlaceList.value = temp
                }
            }
            return ActionResult.Success(Unit)
        }
        return ActionResult.Fail("")
    }

    override suspend fun exitMeet(meetId: Int, userId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.deleteMeet(meetId, userId).transform { result ->
            if (result is ApiResult.SuccessEmpty) {
                val temp = _meetDetailList.value.toMutableList()
                temp.removeIf {
                    it.id == meetId
                }
                _meetDetailList.value = temp
                emit(ActionResult.Success(Unit))
            } else {
                emit(ActionResult.Fail(""))
            }
        }.first()
    }

    override suspend fun clearMeetDetail() {
        _meetInviteStatus.value = emptyList()
        _meetPlaceList.value = emptyMap()
        _commentList.value = emptyMap()
    }

    /**
     * fcm 코드 101 장소추가일때 동작하는 함수
     */
    fun addPlaceToMeeting(id: Int, newPlace: Place) {
        val temp = _meetPlaceList.value.toMutableMap()
        val myPlaceList = temp[id]?.toMutableList() ?: mutableListOf()

        myPlaceList.add(newPlace)  // 새 Place 추가!
        temp[id] = myPlaceList
        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 102 장소삭제일 때 동작하는 함수
     */
    fun deletePlaceFromMeeting(id: Int) {
        val temp = _meetPlaceList.value.toMutableMap()

        temp.forEach { (meetingId, placeList) ->
            val updatedList = placeList.filterNot { it.id == id }
            temp[meetingId] = updatedList
        }
        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 104 모임수락일때 동작하는 함수
     */
    fun updatePlaceStatusToPicked(placeId: Int, newStatus: String) {
        val temp = _meetPlaceList.value.toMutableMap()
        val placeList = temp[placeId]?.toMutableList() ?: return

        val updatedList = placeList.map { place ->
            if (place.id == placeId) {
                place.copy(status = newStatus)
            } else place
        }
        temp[placeId] = updatedList
        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 105 장소 좋아요 업데이트일 때 동작하는 함수
     */
    fun updatePlaceLike(id: Int, placeLike: List<Int>) {
        val temp = _meetPlaceList.value.toMutableMap()

        temp.forEach { (meetingId, placeList) ->
            val updatedList = placeList.map { place ->
                if (place.id == id) {
                    place.copy(likeUserList = placeLike)  // 좋아요 수 수정
                } else {
                    place
                }
            }
            temp[meetingId] = updatedList
        }
        _meetPlaceList.value = temp
    }



}