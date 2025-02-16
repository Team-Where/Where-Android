package com.sooum.domain.repository

import com.sooum.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 *  User관련 처리 를 가진 UserRepository 인터페이스
 */
interface UserRepository {

    /**
     * 모든 User 목록을 가져옵니다.
     */
    fun getUserList() : Flow<List<User>>
}