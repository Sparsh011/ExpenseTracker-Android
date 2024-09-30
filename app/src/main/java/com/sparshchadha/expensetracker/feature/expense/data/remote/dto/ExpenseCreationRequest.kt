package com.sparshchadha.expensetracker.feature.expense.data.remote.dto

data class ExpenseCreationRequest(
    val createdOnDate: String,
    val createdAtTime: String,
    val updatedOnDate: String,
    val updatedAtTime: String,
    val amount: Double,
    val category: String,
    val isRecurring: Boolean = false,
    val recurAfterDays: Int = -1,
    val currency: String = "INR",
    val title: String,
    val description: String
)