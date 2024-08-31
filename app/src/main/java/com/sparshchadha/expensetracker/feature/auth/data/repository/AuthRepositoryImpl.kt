package com.sparshchadha.expensetracker.feature.auth.data.repository

import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.storage.shared_preference.ExpenseTrackerSharedPref

class AuthRepositoryImpl(
    private val sharedPref: ExpenseTrackerSharedPref,
) : AuthRepository {
    private val ACCESS_TOKEN_KEY = "expense_tracker_refresh_token"
    private val REFRESH_TOKEN_KEY = "expense_tracker_refresh_token"

    override fun getAccessToken(): String = sharedPref.getString(key = ACCESS_TOKEN_KEY)

    override fun saveAccessToken(token: String) {
        sharedPref.saveString(
            key = ACCESS_TOKEN_KEY,
            value = token
        )
    }

    override fun saveRefreshToken(token: String) {
        sharedPref.saveString(
            key = REFRESH_TOKEN_KEY,
            value = token
        )
    }

    override fun getRefreshToken(): String = sharedPref.getString(REFRESH_TOKEN_KEY)
}