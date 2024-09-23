package com.sparshchadha.expensetracker.feature.expense.data.remote.dto

data class ExpenseCreationRequest(
    val createdAt: String,
    val updatedAt: String,
    val amount: Double,
    val category: String,
    val isRecurring: Boolean = false,
    val recurAfterDays: Int = -1,
    val currency: String = "INR",
    val title: String,
    val description: String
)