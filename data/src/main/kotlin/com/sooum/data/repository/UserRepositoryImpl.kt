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
            User(1, "C_tester1", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fd9Iv4o%2FbtsMln2JnID%2FI8WURonJdDrWDHzEIGsmVk%2Fimg.png", false),
            User(2, "A_tester2", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbf1DwG%2FbtsMkKxg8Tl%2FYaMbRV8TsGVBKvfwwdAFY0%2Fimg.png", false),
            User(3, "B_tester3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fdf7dZ9%2FbtsMkZOs04N%2FGNr5VKFmoV56ucbOJL7pz1%2Fimg.png", true),
            User(4, "G_tester3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmK40G%2FbtsJqkf3kJO%2FoI8qV3pEjIvoqs2uJulCKK%2Fimg.png", false),
            User(5, "Q_tester3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FrRij7%2FbtsJqareHV1%2FHL6bxVNCKN5RDsDPNffkcK%2Fimg.png", true),
            User(6, "U_tester3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcnILSI%2FbtsJp5Q5Nc0%2FIlVZ0SixxRzoqDIKFDLlHK%2Fimg.png", false),
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