package com.sparshchadha.expensetracker.feature.profile.domain.model;

enum class UserField(val apiPath: String) {
    NAME("name"),
    EMAIL("email"),
    PROFILE_PICTURE("profile_uri"),
    EXPENSE_BUDGET("expense_budget")
}