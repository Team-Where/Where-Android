package com.sooum.domain.repository

import com.sooum.domain.model.MeetDetail
import kotlinx.coroutines.flow.Flow

interface MeetDetailRepository {

    fun getMeetDetailList() : Flow<List<MeetDetail>>
}