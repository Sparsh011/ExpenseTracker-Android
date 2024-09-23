package com.sparshchadha.expensetracker.core.domain

enum class ExpenseCategories(val displayName: String) {
    FUEL("Fuel"),
    FOOD("Food"),
    TRAVEL("Travel"),
    E_COMMERCE("E-Commerce"),
    ENTERTAINMENT("Entertainment"),
    MEDICAL("Medical"),
    INSURANCE("Insurance"),
    MOBILE_RECHARGE("Recharge"),
    OTT_SUBSCRIPTION("OTT"),
    UTILITY_BILL("Utility"),
    BROADBAND("Broadband"),
    OTHER("Other");

    override fun toString(): String {
        return displayName
    }
}
