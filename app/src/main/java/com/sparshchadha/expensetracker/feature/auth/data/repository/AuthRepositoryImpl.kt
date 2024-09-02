package com.sparshchadha.expensetracker.feature.auth.data.repository

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
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
    private val ACCESS_TOKEN_KEY = "expense_tracker_access_token"
    private val REFRESH_TOKEN_KEY = "expense_tracker_refresh_token"

    override suspend fun continueWithPhone(
        request: PhoneAuthRequest,
    ): Flow<Resource<ContinueWithPhoneResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.continueWithPhone(request)

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
                emit(
                    Resource.Error(
                        data = null,
                        error = e
                    )
                )
            }
        }
    }

    override fun retryPhoneAuth(request: RetryPhoneAuthRequest): Flow<Resource<ContinueWithPhoneResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.resendOtp(request)

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
                emit(
                    Resource.Error(
                        data = null,
                        error = e
                    )
                )
            }

        }
    }

    override fun verifyOtp(
        request: VerifyOtpRequest,
    ): Flow<Resource<OtpVerificationResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.verifyOtp(request)

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