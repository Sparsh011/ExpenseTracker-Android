package com.sparshchadha.expensetracker.feature.auth.domain.usecase

import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(token: String) {
        authRepository.saveAccessToken(token)
    }
}