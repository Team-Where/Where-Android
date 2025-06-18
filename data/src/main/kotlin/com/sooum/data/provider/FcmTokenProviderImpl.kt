package com.sooum.data.provider

import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.provider.FcmTokenProvider
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class FcmTokenProviderImpl @Inject constructor(
    private val appDataStore: AppManageDataStore
) : FcmTokenProvider {

    override suspend fun getSavedFcmToken(): String? {
        return appDataStore.getSavedFcmToken().firstOrNull()
    }

    override suspend fun setSavedFcmToken(token: String) {
        appDataStore.setSavedFcmToken(token)
    }
}
