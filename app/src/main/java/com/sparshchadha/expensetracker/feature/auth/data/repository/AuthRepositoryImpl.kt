package com.sparshchadha.expensetracker.feature.auth.data.repository

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyGoogleIdTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.storage.shared_preference.ExpenseTrackerSharedPref
import com.sparshchadha.expensetracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val sharedPref: ExpenseTrackerSharedPref,
    private val api: ExpenseTrackerAPI,
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