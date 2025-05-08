package com.sooum.domain.usecase.friend

import com.sooum.domain.repository.FriendRepository
import javax.inject.Inject

class UpdateFriendFavoriteUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(id: Int, favorite: Boolean) {
        return repository.updateFriendFavorite(id, favorite)
    }
}