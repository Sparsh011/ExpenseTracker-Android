package com.sparshchadha.expensetracker.feature.auth.domain.usecase

import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<String> {
        return authRepository.getAccessToken()
    }
}