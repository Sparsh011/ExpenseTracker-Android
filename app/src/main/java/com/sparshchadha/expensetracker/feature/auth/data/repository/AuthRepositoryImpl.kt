package com.sparshchadha.expensetracker.feature.auth.data.repository

import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.storage.datastore.ExpenseTrackerDataStorePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val dataStorePreference: ExpenseTrackerDataStorePreference,
) : AuthRepository {

    override fun getAccessToken(): Flow<String> = flow {
        dataStorePreference.readAccessToken
    }

    override suspend fun saveAccessToken(token: String) {
        dataStorePreference.saveAccessToken(token = token)
    }
}