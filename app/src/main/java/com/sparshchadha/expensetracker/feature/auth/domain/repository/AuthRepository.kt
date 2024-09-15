package com.sparshchadha.expensetracker.feature.auth.domain.repository

import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyGoogleIdTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun validateOtpVerificationToken(request: VerifyOtpRequest): Flow<Resource<UserVerificationResponse>>

    fun validateGoogleIdToken(request: VerifyGoogleIdTokenRequest): Flow<Resource<UserVerificationResponse>>

    fun getAccessToken(): String

    fun saveAccessToken(token: String)

    fun saveRefreshToken(token: String)

    fun getRefreshToken(): String
}