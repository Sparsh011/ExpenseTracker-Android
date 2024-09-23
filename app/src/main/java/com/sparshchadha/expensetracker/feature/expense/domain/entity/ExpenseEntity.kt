package com.sparshchadha.expensetracker.feature.expense.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sparshchadha.expensetracker.feature.expense.data.remote.dto.ExpenseCreationRequest

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int? = null,
    val createdAt: String,
    val updatedAt: String,
    val amount: Double,
    val category: String,
    val isRecurring: Boolean = false,
    val recurAfterDays: Int = -1,
    val currency: String = "INR",
    val title: String,
    val description: String
) {
    fun ExpenseEntity.toExpenseCreationRequest(): ExpenseCreationRequest {
        return ExpenseCreationRequest(
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            amount = this.amount,
            isRecurring = this.isRecurring,
            recurAfterDays = this.recurAfterDays,
            currency = this.currency,
            title = this.title,
            description = this.description,
            category = this.category
        )
    }
}
