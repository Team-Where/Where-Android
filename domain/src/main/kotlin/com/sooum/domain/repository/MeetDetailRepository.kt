package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Comment
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.NewMeetResult
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
import com.sooum.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MeetDetailRepository {

    suspend fun loadMeetDetailList(userId: Int)
    fun getMeetDetailList() : Flow<List<MeetDetail>>

    fun getMeetDetailById(meetId: Int) : Flow<MeetDetail?>

    suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ) : ActionResult<NewMeetResult>

    suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
        naverLink: String?
    )

}