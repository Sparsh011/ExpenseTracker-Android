package com.sparshchadha.expensetracker.storage.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "expense_tracker_preference")

class ExpenseTrackerDataStorePreference @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val dataStorePreference = context.dataStore


    val readAccessToken: Flow<String?>
        get() = dataStorePreference.data.map { pref ->
            pref[ACCESS_TOKEN_KEY]
        }

    suspend fun saveAccessToken(token: String) {
        dataStorePreference.edit { pref ->
            pref[ACCESS_TOKEN_KEY] = token
        }
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
    }
}