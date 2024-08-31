package com.sparshchadha.expensetracker.feature.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): String

    fun saveAccessToken(token: String)

    fun saveRefreshToken(token: String)

    fun getRefreshToken(): String
}