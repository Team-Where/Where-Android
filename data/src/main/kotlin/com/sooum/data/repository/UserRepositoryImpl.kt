package com.sooum.data.repository

import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * UserRepository 구현체
 * @see[UserRepository]
 */
class UserRepositoryImpl @Inject constructor(

) : UserRepository {

    // 초기 더미 데이터
    private val _userListFlow = MutableStateFlow(
        listOf(
            User(1, "C_tester1", "", false),
            User(2, "A_tester2", "", false),
            User(3, "B_tester3", "", true),
            User(4, "G_tester3", "", false),
            User(5, "Q_tester3", "", true),
            User(6, "U_tester3", "", false),
            User(7, "K_tester3", "", true),
        ).sortedBy { it.name }
    )
    val userListFlow: StateFlow<List<User>> = _userListFlow.asStateFlow()

    override fun getUserList(): Flow<List<User>> {
        return userListFlow
    }

    override suspend fun updateUserFavorite(id: Long, favorite: Boolean) {
        _userListFlow.value = _userListFlow.value.map { user ->
            if (user.id == id) user.copy(isFavorite = favorite) else user
        }
    }

    override suspend fun deleteUser(id: Long) {
        _userListFlow.value = _userListFlow.value.filter { it.id != id }
    }

}