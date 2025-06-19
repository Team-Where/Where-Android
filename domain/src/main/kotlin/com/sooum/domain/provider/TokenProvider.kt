package com.sooum.domain.provider

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(token: String)

    suspend fun getUserId(): Int?
    suspend fun clearAllUserData()
}