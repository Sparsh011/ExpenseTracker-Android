package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class OtpVerificationResponse(
    val isOTPVerified: Boolean?,
    val message: String?,
    val access: String?,
    val refresh: String?
)
