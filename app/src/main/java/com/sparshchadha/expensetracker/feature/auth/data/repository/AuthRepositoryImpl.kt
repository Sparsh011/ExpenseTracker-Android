package com.sparshchadha.expensetracker.feature.auth.data.repository

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyGoogleIdTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.core.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val sharedPref: ExpenseTrackerSharedPref,
    private val api: ExpenseTrackerAPI,
    private val profileRepository: ProfileRepository
) : AuthRepository {

    override fun validateOtpVerificationToken(
        request: VerifyOtpRequest,
    ): Flow<Resource<UserVerificationResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.validateOtpVerificationToken(request)

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

    override fun validateGoogleIdToken(
        request: VerifyGoogleIdTokenRequest
    ) : Flow<Resource<UserVerificationResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.validateGoogleIdToken(request)

            if (response.isSuccessful) {
                val userName = response.body()?.userProfile?.name ?: "Guest"
                val expenseBudget = response.body()?.userProfile?.expenseBudget ?: -1

                profileRepository.saveUserNameLocally(name = userName)
                profileRepository.saveExpenseBudgetLocally(expenseBudget.toString())

                emit(Resource.Success(data = response.body()))
            } else {
                emit(
                    Resource.Error(
                        data = null,
                        error = Throwable(message = response.errorBody().toString())
                    )
                )
            }
        }  catch (e: Exception) {
            emit(Resource.Error(data = null, error = e))
        }
    }

    override fun getAccessToken(): String = sharedPref.getAccessToken()

    override fun saveAccessToken(token: String) {
        sharedPref.saveAccessToken(token)
    }

    override fun saveRefreshToken(token: String) {
        sharedPref.saveRefreshToken(token)
    }

    override fun getRefreshToken(): String = sharedPref.getRefreshToken()
}