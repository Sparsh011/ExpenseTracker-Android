package com.sparshchadha.expensetracker.core.domain

enum class ExpenseCategories(val displayName: String) {
    FUEL("Fuel"),
    FOOD("Food"),
    TRAVEL("Travel"),
    GROCERY("Grocery"),
    MOVIES("Movies"),
    MEDICAL("Medical"),
    INSURANCE("Insurance"),
    MOBILE_RECHARGE("Recharge"),
    OTT_SUBSCRIPTION("OTT"),
    BROADBAND("Broadband"),
    OTHER("Other");

    override fun toString(): String {
        return displayName
    }
}
