package com.sparshchadha.expensetracker.feature.auth.domain.repository

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun continueWithPhone(request: PhoneAuthRequest): Flow<Resource<ContinueWithPhoneResponse>>

    fun retryPhoneAuth(request: RetryPhoneAuthRequest): Flow<Resource<ContinueWithPhoneResponse>>

    fun verifyOtp(request: VerifyOtpRequest): Flow<Resource<OtpVerificationResponse>>

    fun getAccessToken(): String

    fun saveAccessToken(token: String)

    fun saveRefreshToken(token: String)

    fun getRefreshToken(): String
}