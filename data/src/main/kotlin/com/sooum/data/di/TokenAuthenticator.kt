package com.sooum.data.di

import com.sooum.data.network.auth.AuthApi
import com.sooum.domain.provider.TokenProvider
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        val refreshToken = tokenProvider.getRefreshToken() ?: return null

        val newAccessToken = runBlocking {
            runCatching {
                val refreshResponse = authApi.refreshToken("Bearer $refreshToken")
                if (refreshResponse.isSuccessful) {
                    refreshResponse.headers()["Authorization"]
                        ?.removePrefix("Bearer ")
                        ?.trim()
                        ?.also { tokenProvider.saveAccessToken(it) }
                } else null
            }.getOrNull()
        } ?: return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}


