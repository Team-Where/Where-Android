package com.sooum.data.repository

import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * UserRepository 구현체
 * @see[UserRepository]
 */
class UserRepositoryImpl(

) : UserRepository {

    override fun getUserList(): Flow<List<User>> {
        // fake Data from rest
        return flow {
            //3초정도 지연 해주고..
            delay(3000L)
            //이런 데이터가 왔다 가정
            val dummyData = listOf(
                User(1, "C_tester1", "", false),
                User(2, "A_tester2", "", false),
                User(3, "B_tester3", "", true),
                User(4, "G_tester3", "", false),
                User(5, "Q_tester3", "", true),
                User(6, "U_tester3", "", false),
                User(6, "K_tester3", "", true),
            )
            val sortedData = dummyData.sortedBy {
                it.name
            }
            emit(sortedData)
        }.flowOn(Dispatchers.IO)
    }
}