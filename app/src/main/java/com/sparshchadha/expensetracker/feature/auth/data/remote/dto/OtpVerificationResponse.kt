package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class OtpVerificationResponse(
    val isOtpVerified: Boolean?,
    val message: String?,
    val access: String?,
    val refresh: String?
)
