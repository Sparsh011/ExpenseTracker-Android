package com.sparshchadha.expensetracker.feature.auth.data.remote.dto

data class AccessTokenResponse(
    val access: String,
    val refresh: String,
) {
    fun areTokensEmpty(): Boolean =
        access.isEmpty() || refresh.isEmpty()

}