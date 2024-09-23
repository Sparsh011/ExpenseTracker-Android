package com.sparshchadha.expensetracker.core.network.api

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyGoogleIdTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.expense.data.remote.dto.ExpenseCreationResponse
import com.sparshchadha.expensetracker.feature.expense.data.remote.dto.ExpenseCreationRequest
import com.sparshchadha.expensetracker.feature.expense.data.remote.dto.ExpenseUpdateResponse
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UpdateProfileFieldRequest
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UpdateProfileFieldResponse
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ExpenseTrackerAPI {
    @POST("user/refresh")
    suspend fun newAccessToken(
        @Body accessTokenRequest: AccessTokenRequest
    ): Response<AccessTokenResponse>

    @GET("/user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") authorization: String
    ): Response<UserProfile>

    @POST("/login/otp/verify-token")
    suspend fun validateOtpVerificationToken(
        @Body verifyTokenRequest: VerifyOtpRequest
    ): Response<UserVerificationResponse>

    @POST("/login/gmail/verify-token")
    suspend fun validateGoogleIdToken(
        @Body verifyGoogleIdTokenRequest: VerifyGoogleIdTokenRequest
    ): Response<UserVerificationResponse>

    @POST("/user/profile")
    suspend fun updateProfileField(
        @Header("Authorization") authorization: String,
        @Body updateProfileFieldRequest: UpdateProfileFieldRequest
    ): Response<UpdateProfileFieldResponse>

    @POST("/expense")
    suspend fun createExpense(
        @Header("Authorization") authorization: String,
        @Body expenseRequest: ExpenseCreationRequest
    ): Response<ExpenseCreationResponse>

    @PATCH("/expense")
    suspend fun updateExpense(
        @Header("Authorization") authorization: String,
        @Body expenseRequest: ExpenseCreationRequest
    ): Response<ExpenseUpdateResponse>

    companion object {
        const val BASE_URL = "https://expensetracker-engine.onrender.com"
    }
}