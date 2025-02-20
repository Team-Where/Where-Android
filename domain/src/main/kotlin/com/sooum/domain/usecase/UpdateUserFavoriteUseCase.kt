package com.sooum.domain.usecase

import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserFavoriteUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Long, favorite: Boolean) {
        return repository.updateUserFavorite(id, favorite)
    }
}