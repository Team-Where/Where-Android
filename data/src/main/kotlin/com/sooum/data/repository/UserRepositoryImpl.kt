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
            //1초정도 지연 해주고..
            delay(1000L)
            //이런 데이터가 왔다 가정
            emit(
                listOf(
                    User(1,"tester1",""),
                    User(2,"tester2",""),
                    User(3,"tester3",""),
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}