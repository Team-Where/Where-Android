package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.repository.TokenProvider
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class TokenProviderImpl @Inject constructor(
    private val appDataStore: AppManageDataStore
) : TokenProvider {

    override fun getAccessToken(): String? {
        return runBlocking {
            appDataStore.getAccessToken().firstOrNull()
        }
    }

    override fun getRefreshToken(): String? {
        return runBlocking {
            appDataStore.getRefreshToken().firstOrNull()
        }
    }

    override suspend fun getUserId(): Int? = appDataStore.getUserId().firstOrNull()

    override fun saveAccessToken(token: String) {
        runBlocking {
            appDataStore.saveAccessToken(token)
        }
    }
}
