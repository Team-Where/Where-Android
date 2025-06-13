package com.sooum.domain.repository

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(token: String)

    suspend fun getUserId(): Int?
    suspend fun clearAllUserData()
}