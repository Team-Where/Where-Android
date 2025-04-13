package com.sooum.domain.usecase.meet.invite

import com.sooum.domain.datasource.MeetRemoteDataSource
import javax.inject.Inject

class GetMeetInviteLinkUseCase @Inject constructor(
    private val meetRemoteDataSource: MeetRemoteDataSource
) {
    suspend operator fun invoke(link :String) =
        meetRemoteDataSource.getMeetInvite(link)
}