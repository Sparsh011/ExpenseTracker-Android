package com.sparshchadha.expensetracker.feature.profile.domain.repository

import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.domain.model.ProfileFieldUpdateResult
import com.sparshchadha.expensetracker.feature.profile.domain.model.UserField
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(access: String): Flow<Resource<UserProfile>>

    suspend fun readUserName(): Flow<String?>

    suspend fun readProfileUri(): Flow<String?>

    // functions for saving locally are created so that fields can be stored just after
    // login is complete and we don't unnecessarily send a request to the server.
    suspend fun saveUserNameLocally(name: String)

    suspend fun saveProfileUriLocally(uri: String)

    suspend fun saveExpenseBudgetLocally(budget: String)

    suspend fun getTotalExpenditure(): Flow<String?>

    suspend fun readExpenseBudget(): Flow<String?>

    suspend fun updateUserField(
        userField: UserField,
        newValue: String,
        accessToken: String
    ): Flow<Resource<ProfileFieldUpdateResult>>
}