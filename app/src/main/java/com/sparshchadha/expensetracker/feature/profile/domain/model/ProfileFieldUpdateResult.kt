package com.sparshchadha.expensetracker.feature.profile.domain.model

data class ProfileFieldUpdateResult(
    val isSuccess: Boolean,
    val field: String,
    val errorMessage: String?
)