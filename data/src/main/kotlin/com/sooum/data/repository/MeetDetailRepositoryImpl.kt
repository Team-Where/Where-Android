package com.sooum.data.repository

import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.meet.request.AddMeetRequest
import com.sooum.data.network.meet.request.DeleteMeetRequest
import com.sooum.data.network.meet.request.EditMeetRequest
import com.sooum.data.network.meet.request.InviteMeetRequest
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class MeetDetailRepositoryImpl @Inject constructor(
    private val meetApi: MeetApi
) : MeetDetailRepository {

    private val _meetDetailList = MutableStateFlow(
        listOf(
            MeetDetail(
                1,
                "2024 연말파티\uD83E\uDD42",
                "벌써 연말이다 신나게 놀아보장~~",
                "https://s3-alpha-sig.figma.com/img/c8d6/dcd3/d0cf1a8b848a3b713165544ecf9c6479?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=krYvjufSoqEAyn8acYCSG~-QUOROkCxVJUZM0JjokvSbO2Tcp9ukcsdTS0jCIhgFtpnODglNx-S-djkLy7DLJTwmX7gYCwEixyFT71peeBIssSulRl0~dMmtr8LPjmfPHAw2uADh7e~8WZJELBuE6gultPGoNSBFhEYdIXXoLgRscwHeJgwBTBjOYFf8N9pIQSwmSP-OsBdz9~LZQUKX1CisOb8yJtTx8SrPapdSMXYNickk~zQ7PaqfAeAXxyieTIGxSlNjp8QYzQhQrWcXkAFM9Y2xfNiArxvrJIKX-XykeplJAAIcwZ6U25H3UxA6F37LN7dBh1TjXr3VEwxEmA__",
                2025,
                1,
                26
            ),
            MeetDetail(
                2,
                "행궁동 갈 사람\uD83C\uDF42",
                "선선해진 날씨에 같이 사람~!",
                "https://s3-alpha-sig.figma.com/img/aadb/e842/9b7456a077b223a1fdd7ce6ce4e4c046?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=bdatUU4QM18aBwb8waNdC4gX2mo2eT7J3FQM3Mysof0N624upN~0lo8BDm8p5WizzgOqcEFdjmG0mcUs8XBjGW7nXTo4aGiJOWyketQx8hOQlSoA3fJlnIM9fXyela2EhHrYWAJFZFAst~gc8Ox4xoGqc78iZ09hF9PDlWRMlMIME-WYycm9wBpcdauRS90mmCDX5CBoeawZqixOc~qfCBay5FE4~h~wc4vL89RKzoIAjOqPC14hL-ezdlr5SN~WDlWfN~l1a397hIYYsk-ytIw6BLTm~aOqnHKsPpzpXrHXoSkQR4AD9Rlnhoxpi4qNxtV2NUwQmM0Db3hioP7dkg__",
                2024,
                12,
                11
            ),
            MeetDetail(
                3,
                "행궁동 갈 사람\uD83C\uDF42",
                "선선해진 날씨에 같이 사람~!",
                "",
                2025,
                1,
                27
            )
        )
    )

    val meetDetailList
        get() = _meetDetailList.asStateFlow()

    override fun getMeetDetailList(): Flow<List<MeetDetail>> {
        return meetDetailList.transform { dataList ->
            emit(
                dataList.sortedWith(
                    comparator = compareBy({ -it.year }, { -it.month }, { -it.day })
                )
            )
        }
    }

    override fun getMeetDetailById(id: Int?): Flow<MeetDetail?> {
        return meetDetailList.transform { dataList ->
            emit(
                dataList.find { it.id == id }
            )
        }
    }

    override suspend fun updateMeetDetailSchedule(id: Int, schedule: Schedule) {
        val temp = _meetDetailList.value.toMutableList()
        temp.find {
            it.id == id
        }?.let { findItem ->
            val index = temp.indexOf(findItem)
            val newItem = findItem.copy(schedule = schedule)
            temp[index] = newItem
            _meetDetailList.value = temp
        }
    }

    private val json = Json {
        encodeDefaults = true
    }

    override suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ): Flow<ApiResult<Meet>> {

        val request = AddMeetRequest(
            title,
            fromId,
            description,
            participants
        )

        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())

        val imagePart = imageFile?.let { file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
        return safeFlow { meetApi.addMeet(dataPart, imagePart) }
    }

    override suspend fun editMeet(
        id: Int,
        title: String?,
        description: String?,
        finished: Boolean?,
        imageFile: File?
    ): Flow<ApiResult<Meet>> {
        val request = EditMeetRequest(
            id,
            title,
            description,
            finished
        )
        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())
        val imagePart = imageFile?.let { file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
        return safeFlow { meetApi.editMeet(dataPart, imagePart) }
    }

    override suspend fun deleteMeet(
        meetId: Int,
        userId: Int
    ): Flow<ApiResult<Any>> {
        val request = DeleteMeetRequest(
            meetId,
            userId
        )
        return safeFlow { meetApi.deleteMeet(request) }
    }

    override suspend fun getMeetList(userId: Int): Flow<ApiResult<List<Meet>>> {
        return safeFlow { meetApi.getMeetList(userId) }
    }

    override suspend fun inviteMeet(
        meetId: Int,
        fromUserId: Int,
        toUserId: Int
    ): Flow<ApiResult<Any>> {
        val request = InviteMeetRequest(
            meetId,
            fromUserId,
            toUserId
        )
        return safeFlow { meetApi.inviteMeet(request) }
    }
}