package com.sooum.domain.usecase.auth

import jakarta.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
) {
    operator fun invoke(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!~@])[A-Za-z\\d!~@]{8,32}$"
        return password.matches(passwordPattern.toRegex())
    }
}
