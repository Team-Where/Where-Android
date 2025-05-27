package com.sooum.data.repository

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.data.network.TokenProvider
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.firstOrNull

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

    override fun saveAccessToken(token: String) {
        runBlocking {
            appDataStore.saveAccessToken(token)
        }
    }
}
