package com.sooum.domain.usecase.friend

import com.sooum.domain.model.ActionResult
import com.sooum.domain.repository.FriendRepository
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import javax.inject.Inject

class UpdateFriendFavoriteUseCase @Inject constructor(
    private val repository: FriendRepository,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase
) {
    suspend operator fun invoke(id: Int): ActionResult<*> {
        return repository.updateFriendFavorite(
            friendId = id,
            userId = getLoginUserIdUseCase()!!
        )
    }
}