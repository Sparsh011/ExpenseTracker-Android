package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class User(
    val isOTPVerified: Boolean,
    val requestId: String,
    val message: String,
    val access: String,
    val refresh: String
)
