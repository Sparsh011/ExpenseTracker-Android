package com.sparshchadha.expensetracker.feature.expense.presentation

import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.core.domain.ExpenseCategories

object ExpenseCategoryMapper {
    fun getIconForCategory(category: ExpenseCategories): Int {
        return when (category) {
            ExpenseCategories.FUEL -> R.drawable.ic_fuel
            ExpenseCategories.FOOD -> R.drawable.ic_food
            ExpenseCategories.TRAVEL -> R.drawable.ic_travel
            ExpenseCategories.GROCERY -> R.drawable.ic_grocery
            ExpenseCategories.MOVIES -> R.drawable.ic_movies
            ExpenseCategories.OTT_SUBSCRIPTION -> R.drawable.ic_ott
            ExpenseCategories.INSURANCE -> R.drawable.ic_insurance
            ExpenseCategories.MOBILE_RECHARGE -> R.drawable.ic_mobile_recharge
            ExpenseCategories.OTHER -> R.drawable.ic_other_expense
            ExpenseCategories.MEDICAL -> R.drawable.ic_medical
            ExpenseCategories.BROADBAND -> R.drawable.ic_broadband
            ExpenseCategories.INVESTMENT -> R.drawable.ic_investment
        }
    }

    fun getIconForCategory(category: String): Int {
        val expenseCategory = ExpenseCategories.entries.firstOrNull {
            it.name.equals(category, ignoreCase = true)
        }

        return expenseCategory?.let {
            getIconForCategory(it)
        } ?: R.drawable.ic_other_expense
    }
}

