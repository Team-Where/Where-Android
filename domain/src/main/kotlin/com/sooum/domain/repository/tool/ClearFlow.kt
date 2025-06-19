package com.sooum.domain.repository.tool

interface ClearFlow {
    suspend fun clearWhenLogout()
}