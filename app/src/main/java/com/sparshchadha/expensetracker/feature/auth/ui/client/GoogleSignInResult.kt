package com.sparshchadha.expensetracker.feature.auth.ui.client

data class GoogleSignInResult(
    val idToken: String = "",
    val errorMessage: String = "",
    val isSuccessful: Boolean
)
