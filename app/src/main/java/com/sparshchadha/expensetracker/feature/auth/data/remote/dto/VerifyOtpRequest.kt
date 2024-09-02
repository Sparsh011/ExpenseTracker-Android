package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class VerifyOtpRequest(
    val phoneNumber: String,
    val otp: String,
    val orderId: String
)
