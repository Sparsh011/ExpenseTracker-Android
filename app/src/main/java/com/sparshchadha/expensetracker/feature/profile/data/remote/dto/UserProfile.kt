package com.sparshchadha.expensetracker.feature.profile.data.remote.dto

data class UserProfile(
    val verificationTime: String,
    val expenseBudget: Int,
    val name: String,
    val phoneNumber: String,
    val emailId: String,
    val profileUri: String
)
