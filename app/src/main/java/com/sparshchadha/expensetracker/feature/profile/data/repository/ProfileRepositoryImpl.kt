package com.sparshchadha.expensetracker.feature.profile.data.repository

import com.sparshchadha.expensetracker.common.utils.Utility
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UpdateProfileFieldRequest
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import com.sparshchadha.expensetracker.core.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.core.storage.datastore.ExpenseTrackerDataStorePreference
import com.sparshchadha.expensetracker.feature.profile.domain.model.ProfileFieldUpdateResult
import com.sparshchadha.expensetracker.feature.profile.domain.model.UserField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val api: ExpenseTrackerAPI,
    private val dataStorePreference: ExpenseTrackerDataStorePreference
) : ProfileRepository {
    override suspend fun getUserProfile(access: String): Flow<Resource<UserProfile>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.getUserProfile(
                    authorization = "Bearer $access",
                )

                Utility.debugLog("Made request, now waiting")

                if (response.isSuccessful) {
                    Utility.debugLog("success response")
                    emit(Resource.Success(data = response.body()))
                    dataStorePreference.saveUserName(response.body()?.name ?: "Guest")
                    dataStorePreference.saveTotalExpenseBudget(response.body()?.expenseBudget.toString())
                    dataStorePreference.saveProfileUri(response.body()?.profileUri ?: "")
                } else {
                    emit(
                        Resource.Error(
                            data = null,
                            error = Throwable(message = response.errorBody().toString())
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, error = e))
            }
        }
    }

    override suspend fun readUserName(): Flow<String?> = dataStorePreference.readUserName

    override suspend fun saveUserNameLocally(name: String) {
        dataStorePreference.saveUserName(name)
    }

    override suspend fun saveProfileUriLocally(uri: String) {
        dataStorePreference.saveProfileUri(uri)
    }

    override suspend fun readProfileUri(): Flow<String?> = dataStorePreference.readProfileUri

    override suspend fun readExpenseBudget(): Flow<String?> =
        dataStorePreference.readTotalExpenseBudget

    override suspend fun saveExpenseBudgetLocally(budget: String) {
        dataStorePreference.saveTotalExpenseBudget(budget)
    }

    override suspend fun getTotalExpenditure(): Flow<String?> = flow {

    }

    override suspend fun updateUserField(
        userField: UserField,
        newValue: String,
        accessToken: String,
    ): Flow<Resource<ProfileFieldUpdateResult>> = flow {
        emit(Resource.Loading())

        try {
            val updateProfileFieldRequest = UpdateProfileFieldRequest(newValue = newValue, field = userField.apiPath)
            val response = api.updateProfileField(
                authorization = "Bearer $accessToken",
                updateProfileFieldRequest = updateProfileFieldRequest
            )

            if (response.isSuccessful) {
                when (userField) {
                    UserField.NAME -> {
                        dataStorePreference.saveUserName(newValue)
                    }
                    UserField.EMAIL -> {
                        // Ignore
                    }
                    UserField.PROFILE_PICTURE -> {
                        dataStorePreference.saveProfileUri(newValue)
                    }
                    UserField.EXPENSE_BUDGET -> {
                        dataStorePreference.saveTotalExpenseBudget(newValue)
                    }
                }
                emit(
                    Resource.Success(
                        ProfileFieldUpdateResult(
                            isSuccess = true,
                            field = userField.name,
                            errorMessage = null
                        )
                    )
                )
            } else {
                emit(
                    Resource.Error(
                        ProfileFieldUpdateResult(
                            isSuccess = false,
                            field = userField.name,
                            errorMessage = "Unable to update ${userField.name}, please try again!"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    ProfileFieldUpdateResult(
                        isSuccess = false,
                        field = userField.name,
                        errorMessage = e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            )
        }
    }
}