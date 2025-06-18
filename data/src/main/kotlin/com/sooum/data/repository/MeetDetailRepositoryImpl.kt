package com.sooum.data.repository

import com.sooum.core.alarm.AlarmMaker
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
import com.sooum.domain.usecase.friend.GetFriendByIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

typealias UserId = Int
typealias PlaceId = Int

class MeetDetailRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource,
    private val alarmMaker: AlarmMaker,
    private val getFriendUseCase: GetFriendByIdUseCase
) : MeetDetailRepository {

    /**
     * 로그인된 유저의 id로 가져온 전체 모임 목록
     */
    private val _meetDetailList = MutableStateFlow(emptyList<MeetDetail>())

    override suspend fun clearWhenLogout() {
        _meetDetailList.update {
            emptyList()
        }
    }

    /**
     * [loadMeetDetailSubData]이후 해당 모임에 해당되는 초대 현황 리스트
     */
    private val _meetInviteStatus = MutableStateFlow(emptyList<MeetInviteStatus>())

    private val meetDetailList
        get() = _meetDetailList.asStateFlow()

    private val meetInviteStatus
        get() = _meetInviteStatus.asStateFlow()

    private fun makeStandardDate(
        date: String?,
        time: String?
    ): LocalDateTime? {
        val dateList = date?.split("-")?.mapNotNull { it.toIntOrNull() }
        val timeList = time?.split(":")?.mapNotNull { it.toIntOrNull() }

        if (dateList?.size != 3) return null

        val (year, month, day) = dateList

        return try {
            when (timeList?.size) {
                3 -> {
                    val (hour, minute, second) = timeList
                    LocalDateTime.of(year, month, day, hour, minute, second)
                }

                2 -> {
                    val (hour, minute) = timeList
                    LocalDateTime.of(year, month, day, hour, minute)
                }

                else -> {
                    null
                }
            }
        } catch (e: Exception) {
            null // 잘못된 날짜/시간 조합일 경우 (예: 2월 30일 등)
        }
    }


    override suspend fun loadMeetDetailList(userId: UserId) {
        val meetDetails = meetRemoteDataSource.getMeetList(userId).first()
        meetDetails.forEach { meetDetail ->
            if (!meetDetail.finished) {
                val time = makeStandardDate(meetDetail.date, meetDetail.time)
                if (time != null) {
                    alarmMaker.makeAlarm(meetDetail.id, meetDetail.title, time)
                }
            }
        }
        _meetDetailList.update {
            meetDetails
        }
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
                    _meetDetailList.update { meetDetailList ->
                        val tempList = meetDetailList.toMutableList()
                        tempList.add(MeetDetail(newMeetData))
                        tempList
                    }
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
                    _meetDetailList.update { meetDetailList ->
                        val tempList = meetDetailList.toMutableList()
                        val item = tempList.first { it.id == meetId }

                        alarmMaker.cancelAlarm(meetId, item.title)
                        tempList.remove(item)

                        tempList
                    }
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index].copy(finished = true)
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index].copy(title = title)
                            makeStandardDate(tempMeet.date, tempMeet.time)?.let {
                                alarmMaker.makeAlarm(meetId, tempMeet.title, it)
                            }
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail =
                                tempList[index].copy(description = description)
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index]
                                .copy(image = data.image)
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
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

    override suspend fun deleteImage(meetId: Int): ActionResult<*> {
        return meetRemoteDataSource.deleteCover(
            meetId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index]
                                .copy(image = null)
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index]
                                .copy(schedule = schedule)
                            makeStandardDate(schedule.date, schedule.time)?.let {
                                alarmMaker.makeAlarm(meetId, tempMeet.title, it)
                            }
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
                        }
                    }
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
                    _meetDetailList.update { meetDetailList ->
                        val index = meetDetailList.indexOfFirst { it.id == meetId }
                        if (index >= 0) {
                            val tempList = meetDetailList.toMutableList()
                            var tempMeet: MeetDetail = tempList[index]
                                .copy(schedule = schedule)
                            makeStandardDate(schedule.date, schedule.time)?.let {
                                alarmMaker.makeAlarm(meetId, tempMeet.title, it)
                            }
                            tempList[index] = tempMeet
                            tempList
                        } else {
                            meetDetailList
                        }
                    }
                    emit(ActionResult.Success(schedule))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun inviteOkFromLink(userId: Int, link: String): ActionResult<MeetDetail> {
        return meetRemoteDataSource.inviteOkFromLink(
            userId,
            link,
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { meetDetail ->
                    _meetDetailList.update { meetDetailList ->
                        val tempList = meetDetailList.toMutableList()
                        tempList.add(meetDetail)
                        tempList
                    }
                    emit(ActionResult.Success(meetDetail))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun invite(meetId: Int, userId: Int, friendId: Int): ActionResult<Unit> {
        return meetRemoteDataSource.inviteMeet(
            meetId,
            userId,
            friendId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    val friend = getFriendUseCase(friendId).firstOrNull()
                    if (friend == null) {
                        emit(ActionResult.Fail("친구 정보를 가져 올 수 없습니다."))
                    } else {
                        _meetInviteStatus.update { list ->
                            val temp = list.toMutableList()
                            val data = MeetInviteStatus(
                                fromId = userId,
                                fromName = "",
                                toId = friendId,
                                toName = friend.name,
                                status = false,
                                toImage = friend.image
                            )
                            temp.add(data)
                            temp
                        }
                        emit(ActionResult.Success(Unit))
                    }
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun clearMeetDetail() {
        _meetInviteStatus.update {
            emptyList()
        }
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

    override suspend fun updateMeetInviteStatus(userId: Int) {
        val updateList = _meetInviteStatus.value.map { meetInviteStatus ->
            if (meetInviteStatus.toId == userId) {
                meetInviteStatus.copy(status = true)
            } else {
                meetInviteStatus
            }
        }
        _meetInviteStatus.value = updateList
    }

}