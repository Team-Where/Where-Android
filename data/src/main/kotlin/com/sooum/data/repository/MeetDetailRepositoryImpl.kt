package com.sooum.data.repository

import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.joinAll
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

    override fun getMeetPlaceList(): Flow<List<PlaceWithUsers>> = meetPlaceList

    override fun getMeetDetailById(meetId: Int): Flow<MeetDetail?> = meetDetailList
        .map { list -> list.find { it.id == meetId } }


    /**
     * ApiResult가 [ApiResult.Success]인 경우 data를 반환하고 이외는 에러 메시지를 반환 합니다.
     */
    private inline fun <T> ApiResult<T>.covertApiResultToActionResultIfSuccess(
        onSuccess: (data: T) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        when (this) {
            is ApiResult.Success -> {
                onSuccess(this.data)
            }

            is ApiResult.Fail.Error -> {
                onFail(this.message ?: "알수 없는 오류가 발생했습니다.")
            }

            is ApiResult.Fail.Exception -> {
                onFail(this.e.localizedMessage ?: "알수 없는 예외가 발생했습니다.")
            }

            else -> {
                onFail("알수 없는 예외")
            }
        }
    }

    /**
     * ApiResult가 [ApiResult.SuccessEmpty]인 경우 성공 이외는 에러 메시지를 반환 합니다.
     */
    private inline fun <T> ApiResult<T>.covertApiResultToActionResultIfSuccessEmpty(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        when (this) {
            is ApiResult.SuccessEmpty -> {
                onSuccess()
            }

            is ApiResult.Fail.Error -> {
                onFail(this.message ?: "알수 없는 오류가 발생했습니다.")
            }

            is ApiResult.Fail.Exception -> {
                onFail(this.e.localizedMessage ?: "알수 없는 예외가 발생했습니다.")
            }

            else -> {
                onFail("알수 없는 예외")
            }
        }
    }

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
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { newMeetData ->
                    val temp = _meetDetailList.value.toMutableList()
                    temp.add(MeetDetail(newMeetData))
                    _meetDetailList.value = temp
                    emit(ActionResult.Success(NewMeetResult(newMeetData)))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun exitMeet(meetId: Int, userId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.deleteMeet(
            meetId = meetId,
            userId = userId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    val temp = _meetDetailList.value.toMutableList()
                    temp.removeIf {
                        it.id == meetId
                    }
                    _meetDetailList.value = temp
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun finishMeet(meetId: Int, userId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.finishMeet(
            meetId,
            userId,
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    val temp = _meetDetailList.value.toMutableList()
                    val index = temp.indexOfFirst { it.id == meetId }
                    if (index >= 0) {
                        var tempMeet: MeetDetail = temp[index]
                        tempMeet = tempMeet.copy(finished = true)
                        temp[index] = tempMeet
                        _meetDetailList.value = temp
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun updateTitle(
        meetId: Int,
        userId: Int,
        title: String
    ): ActionResult<*> {
        return meetRemoteDataSource.editMeet(
            meetId,
            userId,
            title,
            null,
            null
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = {
                    val temp = _meetDetailList.value.toMutableList()
                    val index = temp.indexOfFirst { it.id == meetId }
                    if (index >= 0) {
                        var tempMeet: MeetDetail = temp[index]
                        tempMeet = tempMeet.copy(title = title)
                        temp[index] = tempMeet
                        _meetDetailList.value = temp
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun updateDescription(
        meetId: Int,
        userId: Int,
        description: String
    ): ActionResult<*> {
        return meetRemoteDataSource.editMeet(
            meetId,
            userId,
            null,
            description,
            null
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = {
                    val temp = _meetDetailList.value.toMutableList()
                    val index = temp.indexOfFirst { it.id == meetId }
                    if (index >= 0) {
                        var tempMeet: MeetDetail = temp[index]
                        tempMeet = tempMeet.copy(description = description)
                        temp[index] = tempMeet
                        _meetDetailList.value = temp
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    override suspend fun updateImage(meetId: Int, userId: Int, imageFile: File?): ActionResult<*> {
        return meetRemoteDataSource.editMeet(
            meetId,
            userId,
            null,
            null,
            imageFile
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { data ->
                    val temp = _meetDetailList.value.toMutableList()
                    val index = temp.indexOfFirst { it.id == meetId }
                    if (index >= 0) {
                        var tempMeet: MeetDetail = temp[index]
                        tempMeet = tempMeet.copy(image = data.image)
                        temp[index] = tempMeet
                        _meetDetailList.value = temp
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = { msg ->
                    emit(ActionResult.Fail(msg))
                }
            )
        }.first()
    }

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadMeetDetailSubData(meetId: Int) {
        asyncScope.launch {
            val myId = getLoginUserIdUseCase()!!

            val meetInviteStatusJob = async {
                meetRemoteDataSource.getMeetInviteStatus(
                    meetId = meetId
                )
            }

            val placeListJob = async {
                meetRemoteDataSource.getMeetPlaceList(meetId, myId)
            }

            joinAll(meetInviteStatusJob, placeListJob)

            val meetInviteResult = meetInviteStatusJob.await().first()
            if (meetInviteResult is ApiResult.Success) {
                _meetInviteStatus.value = meetInviteResult.data
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
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { schedule ->
                    val tempList = _meetDetailList.value.toMutableList()
                    val index = tempList.indexOfFirst { it.id == meetId }
                    val item = tempList[index].copy(
                        schedule = schedule
                    )
                    tempList[index] = item
                    _meetDetailList.value = tempList
                    emit(ActionResult.Success(schedule))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
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
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { schedule ->
                    val tempList = _meetDetailList.value.toMutableList()
                    val index = tempList.indexOfFirst { it.id == meetId }
                    val item = tempList[index].copy(
                        schedule = schedule
                    )
                    tempList[index] = item
                    _meetDetailList.value = tempList
                    emit(ActionResult.Success(schedule))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
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
        _meetInviteStatus.value = emptyList()
        _meetPlaceList.value = emptyList()
        _commentList.value = emptyMap()
    }

    /**
     * fcm 코드 101 장소추가일때 동작하는 함수 -> 이거 코드 틀린듯 수정해야할듯 meetingID에 대한 부분 수정해야함
     */
    override suspend fun addPlaceToMeeting(id: Int, newPlace: Place) {
//        val temp = _meetPlaceList.value.toMutableMap()
//        val myPlaceList = temp[id]?.toMutableList() ?: mutableListOf()
//
//        myPlaceList.add(newPlace)  // 새 Place 추가!
//        temp[id] = myPlaceList
//        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 102 장소삭제일 때 동작하는 함수
     */
    override suspend fun deletePlaceFromMeeting(id: Int) {
//        val temp = _meetPlaceList.value.toMutableMap()
//
//        temp.forEach { (meetingId, placeList) ->
//            val updatedList = placeList.filterNot { it.id == id }
//            temp[meetingId] = updatedList
//        }
//        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 104 모임수락일때 동작하는 함수
     */
    override suspend fun updatePlaceStatusToPicked(placeId: Int, newStatus: String) {
//        val temp = _meetPlaceList.value.toMutableMap()
//        val placeList = temp[placeId]?.toMutableList() ?: return
//
//        val updatedList = placeList.map { place ->
//            if (place.id == placeId) {
//                place.copy(status = newStatus)
//            } else place
//        }
//        temp[placeId] = updatedList
//        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 105 장소 좋아요 업데이트일 때 동작하는 함수
     */
    override suspend fun updatePlaceLike(id: Int, placeLike: Int) {
//        val temp = _meetPlaceList.value.toMutableMap()
//
//        temp.forEach { (meetingId, placeList) ->
//            val updatedList = placeList.map { place ->
//                if (place.id == id) {
//                    place.copy(likeCount = placeLike)  // 좋아요 수 수정
//                } else {
//                    place
//                }
//            }
//            temp[meetingId] = updatedList
//        }
//        _meetPlaceList.value = temp
    }

    /**
     * fcm 코드 201,202 일정 수정,추가 일때 함수
     */

    override suspend fun updateSchedule(meetId: Int, date: String, time: String) {
        val updateList = _meetDetailList.value.map { meetDetail ->
            if (meetDetail.id == meetId) {
                meetDetail.copy(schedule = Schedule(meetId, date, time))
            } else {
                meetDetail
            }
        }
        _meetDetailList.value = updateList
    }

    /**
     * fcm 코드 203 일정 삭제 일때 함수
     */
    override suspend fun deleteSchedule(meetId: Int) {
        val updatedList = _meetDetailList.value.map { meetDetail ->
            if (meetDetail.id == meetId) {
                meetDetail.copy(schedule = null)
            } else {
                meetDetail
            }
        }
        _meetDetailList.value = updatedList
    }

}