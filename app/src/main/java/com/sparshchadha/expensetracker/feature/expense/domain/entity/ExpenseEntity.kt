package com.sparshchadha.expensetracker.feature.expense.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sparshchadha.expensetracker.feature.expense.data.remote.dto.ExpenseCreationRequest

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int? = null,
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
    val description: String,
) {
    fun toExpenseCreationRequest(): ExpenseCreationRequest {
        return ExpenseCreationRequest(
            createdAtTime = this.createdAtTime,
            createdOnDate = this.createdOnDate,
            updatedAtTime = this.updatedAtTime,
            updatedOnDate = this.updatedOnDate,
            amount = this.amount,
            isRecurring = this.isRecurring,
            recurAfterDays = this.recurAfterDays,
            currency = this.currency,
            title = this.title,
            description = this.description,
            category = this.category
        )
    }

    fun isValid(): Boolean {
        return if (this.createdOnDate.isBlank()) false
        else if (this.createdAtTime.isBlank()) false
        else if (this.amount == 0.0) false
        else if (category.isBlank()) false
        else if (recurAfterDays == 0) false
        else if (title.isBlank()) false
        else if (description.isBlank()) false
        else true
    }

    fun getInvalidField(): String {
        return if (this.createdOnDate.isBlank()) "Date"
        else if (this.createdAtTime.isBlank()) "Time"
        else if (this.amount == 0.0) "Amount"
        else if (category.isBlank()) "Category"
        else if (title.isBlank()) "Title"
        else if (description.isBlank()) "Description"
        else "recurring days more than 0"
    }
}
