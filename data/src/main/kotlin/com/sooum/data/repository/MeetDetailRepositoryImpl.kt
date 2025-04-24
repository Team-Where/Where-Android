package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
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

    private val meetDetailList
        get() = _meetDetailList.asStateFlow()

    private val meetInviteStatus
        get() = _meetInviteStatus.asStateFlow()


    override suspend fun loadMeetDetailList(userId: UserId) {
        val meetDetails = meetRemoteDataSource.getMeetList(userId).first()
        _meetDetailList.value = meetDetails
    }

    override fun getMeetDetailList(): Flow<List<MeetDetail>> = meetDetailList

    override fun getMeetInviteList(): Flow<List<MeetInviteStatus>> = meetInviteStatus

    override fun getMeetDetailById(meetId: Int): Flow<MeetDetail?> = meetDetailList
        .map { list -> list.find { it.id == meetId } }



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
            val meetInviteStatusJob = async {
                meetRemoteDataSource.getMeetInviteStatus(
                    meetId = meetId
                )
            }
            val meetInviteResult = meetInviteStatusJob.await().first()
            if (meetInviteResult is ApiResult.Success) {
                _meetInviteStatus.value = meetInviteResult.data
            }
        }
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

    override suspend fun inviteOkFromLink(userId: Int, link: String): ActionResult<*> {
        return meetRemoteDataSource.inviteOkFromLink(
            userId,
            link,
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { meet ->
                    val tempList = _meetDetailList.value.toMutableList()
                    tempList.add(MeetDetail(meet))
                    _meetDetailList.value = tempList
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
    }


    override suspend fun updateScheduleFromFcm(meetId: Int, date: String, time: String) {
        val updateList = _meetDetailList.value.map { meetDetail ->
            if (meetDetail.id == meetId) {
                meetDetail.copy(schedule = Schedule(meetId, date, time))
            } else {
                meetDetail
            }
        }
        _meetDetailList.value = updateList
    }


    override suspend fun deleteScheduleFcm(meetId: Int) {
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