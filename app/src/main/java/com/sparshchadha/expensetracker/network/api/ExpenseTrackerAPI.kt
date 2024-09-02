package com.sparshchadha.expensetracker.network.api

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.User
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExpenseTrackerAPI {
    @POST("user/refresh")
    suspend fun newAccessToken(
        @Body accessTokenRequest: AccessTokenRequest
    ): Response<AccessTokenResponse>

    @POST("/login/send-otp")
    suspend fun continueWithPhone(
        @Body continueWithPhoneRequest: PhoneAuthRequest
    ): Response<ContinueWithPhoneResponse>

    @POST("/login/resend-otp")
    suspend fun resendOtp(
        @Body resendOtpRequest: RetryPhoneAuthRequest
    ): Response<ContinueWithPhoneResponse>

    @POST("/login/verify-otp")
    suspend fun verifyOtp(
        @Body verifyOtpRequest: VerifyOtpRequest
    ): Response<User>

    companion object {
        const val BASE_URL = "https://expensetracker-engine.onrender.com"
    }
}