package com.sparshchadha.expensetracker.feature.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): Flow<String>

    suspend fun saveAccessToken(token: String)
}