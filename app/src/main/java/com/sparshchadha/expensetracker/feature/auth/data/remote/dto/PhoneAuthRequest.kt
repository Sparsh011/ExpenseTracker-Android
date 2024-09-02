package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class PhoneAuthRequest (
    val phoneNumber: String,
    val otpLength: Int,
    val expiry: Int // otp expiry in seconds
)