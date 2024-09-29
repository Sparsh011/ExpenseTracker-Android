package com.sparshchadha.expensetracker.feature.auth.domain.usecase

import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): String {
        return authRepository.getAccessToken()
    }
}