package com.sparshchadha.expensetracker.core.storage.shared_preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class ExpenseTrackerSharedPref @Inject constructor(
    private val context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ExpenseTrackerPrefs", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().apply {
            putString(ET_ACCESS_TOKEN_KEY, token)
            apply()
        }
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().apply {
            putString(ET_REFRESH_TOKEN_KEY, token)
            apply()
        }
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(ET_ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun getRefreshToken(): String {
        return sharedPreferences.getString(ET_REFRESH_TOKEN_KEY, "") ?: ""
    }

    fun saveUserId(id: String) {
        sharedPreferences.edit().apply {
            putString(ET_USER_ID_KEY, id)
            apply()
        }
    }

    fun getUserId(): String {
        return sharedPreferences.getString(ET_USER_ID_KEY, "") ?: ""
    }

    companion object {
        // All the keys are stored here
        private const val ET_ACCESS_TOKEN_KEY = "expense_tracker_access_token"
        private const val ET_REFRESH_TOKEN_KEY = "expense_tracker_refresh_token"
        private const val ET_USER_ID_KEY = "expense_tracker_user_id"
    }
}
