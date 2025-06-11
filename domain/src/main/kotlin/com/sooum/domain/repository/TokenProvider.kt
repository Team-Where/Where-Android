package com.sooum.domain.repository

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    suspend fun getUserId(): Int?
    fun saveAccessToken(token: String)
}