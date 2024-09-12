package com.sparshchadha.expensetracker.network.api

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UpdateUserNameRequest
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface ExpenseTrackerAPI {
    @POST("user/refresh")
    suspend fun newAccessToken(
        @Body accessTokenRequest: AccessTokenRequest
    ): Response<AccessTokenResponse>

    @GET("/user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") authorization: String
    ): Response<UserProfile>

    @PATCH("/user/name")
    suspend fun saveUserName(
        @Header("Authorization") authorization: String,
        @Body updateUserNameRequest: UpdateUserNameRequest
    ): Response<Unit>

    @POST("/login/otp/verify-token")
    suspend fun validateOtpVerificationToken(
        @Body verifyTokenRequest: VerifyOtpRequest
    ): Response<OtpVerificationResponse>

    companion object {
        const val BASE_URL = "https://expensetracker-engine.onrender.com"
    }
}