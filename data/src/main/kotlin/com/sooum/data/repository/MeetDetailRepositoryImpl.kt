package com.sooum.data.repository

import android.util.Log
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.io.File
import javax.inject.Inject

class MeetDetailRepositoryImpl @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource
) : MeetDetailRepository {

    private val _meetDetailList = MutableStateFlow(emptyList<MeetDetail>())
    private val _meetInviteStatus = MutableStateFlow(emptyList<MeetInviteStatus>())

    private val meetDetailList
        get() = _meetDetailList.asStateFlow()

    private val meetInviteStatus
        get() = _meetInviteStatus.asStateFlow()

    override suspend fun loadMeetDetailList(userId: Int) {
        val meetDetails = meetRemoteDataSource.getMeetList(userId).first()
        _meetDetailList.value = meetDetails
    }

    override fun getMeetDetailList(): Flow<List<MeetDetail>> = meetDetailList

    override fun getMeetInviteList(): Flow<List<MeetInviteStatus>> = meetInviteStatus

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

    override suspend fun loadInviteStatus(meetId: Int) {
        val result = meetRemoteDataSource.getMeetInviteStatus(
            meetId
        ).first()

        if (result is ApiResult.Success) {
            _meetInviteStatus.value = result.data
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
                val schedule = result.data
//                val tempList = _meetDetailList.value.toMutableList()
//                val index = tempList.indexOfFirst { it.id == meetId }
//                val item = tempList[index].copy(
//                    schedule = schedule
//                )
//                tempList[index] = item
//                _meetDetailList.value = tempList
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
    }
}