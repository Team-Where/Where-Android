package com.sooum.domain.usecase.meet

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Meet
import com.sooum.domain.model.onSuccess
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMeetDetailListUseCase @Inject constructor(
    private val repository: MeetDetailRepository
) {
    suspend operator fun invoke(userId: Int): Flow<List<Meet>> {
        return repository.getMeetList(userId).map { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.data
                }

                else -> {
                    emptyList<Meet>()
                }
            }
        }
    }
}