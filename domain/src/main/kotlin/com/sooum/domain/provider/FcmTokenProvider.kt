package com.sooum.domain.provider

interface FcmTokenProvider {
    suspend fun getSavedFcmToken(): String?
    suspend fun setSavedFcmToken(token: String?)

    suspend fun checkNotificationAllowed(): Boolean
}