package com.sooum.domain.usecase.place

import com.sooum.domain.model.ShareResult
import com.sooum.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * 공유된 데이터로 부터
 */
class AddPlaceUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(id: Int, shareResult: ShareResult) {
        repository.addPlace(id, shareResult)
    }
}