package com.sparshchadha.expensetracker.feature.profile.data.repository

import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UpdateUserNameRequest
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import com.sparshchadha.expensetracker.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val api: ExpenseTrackerAPI,
) : ProfileRepository {
    override suspend fun getUserProfile(access: String): Flow<Resource<UserProfile>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.getUserProfile(
                    authorization = "Bearer $access",
                )

                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()))
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

    override suspend fun saveUserName(newName: String, access: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.saveUserName(
                    authorization = "Bearer $access",
                    updateUserNameRequest = UpdateUserNameRequest(newName)
                )

                if (response.isSuccessful) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error(false, error = Throwable(message = "Unable To Change Name, Please Try Again.")))
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, error = e))
            }
        }
    }
}