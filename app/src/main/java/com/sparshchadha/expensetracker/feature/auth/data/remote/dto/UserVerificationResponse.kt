package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile

data class UserVerificationResponse(
    val isVerified: Boolean?,
    val message: String?,
    val access: String?,
    val refresh: String?,
    val userProfile: UserProfile
)
