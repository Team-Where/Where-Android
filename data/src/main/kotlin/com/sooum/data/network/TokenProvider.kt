package com.sooum.data.network

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(token: String)
}