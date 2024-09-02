package com.sparshchadha.expensetracker.feature.profile.data.remote.dto

import java.sql.Time

data class UserProfile(
    val createdAt: Time,
    val expenseBudget: Int,
    val name: String,
    val phoneNumber: String,
    val emailId: String
)
