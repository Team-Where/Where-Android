package com.sooum.domain.usecase.user

import android.util.Base64
import com.sooum.domain.repository.TokenProvider
import org.json.JSONObject
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * 현재 토큰이 있는지 확인 하고,
 * 있다면 만료되었는지 확인 한다.
 * @retrun 없거나,만료된경우 false , 아니면 true
 */
class CheckUserTokenExpiredUseCase @Inject constructor(
    private val tokenProvider: TokenProvider
) {
    suspend operator fun invoke(): Boolean {
        val refreshToken: String? = tokenProvider.getRefreshToken()
        return if (refreshToken == null) {
            false
        } else {
            val exp = getJwtExpiration(refreshToken)
            val now = System.currentTimeMillis() / 1000
            exp != null && exp > now
        }
    }

    private fun getJwtExpiration(token: String): Long? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = parts[1]
            val decodedBytes =
                Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val json = JSONObject(String(decodedBytes, Charset.forName("UTF-8")))

            json.getLong("exp")
        } catch (e: Exception) {
            null
        }
    }
}