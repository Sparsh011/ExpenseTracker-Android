package com.sparshchadha.expensetracker.feature.profile.data.remote.dto

data class UpdateProfileFieldRequest(
    val newValue: String,
    val field: String
)
