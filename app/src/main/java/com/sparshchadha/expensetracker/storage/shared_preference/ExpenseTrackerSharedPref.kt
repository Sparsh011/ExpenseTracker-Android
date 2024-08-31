package com.sparshchadha.expensetracker.storage.shared_preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class ExpenseTrackerSharedPref @Inject constructor(
    private val context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ExpenseTrackerPrefs", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
}
