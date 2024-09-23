package com.sparshchadha.expensetracker.feature.expense.domain.repository

import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun getAllExpenses(): Flow<List<ExpenseEntity>>
    fun getCurrentDayExpenses(currentDate: String): Flow<List<ExpenseEntity>>

    fun saveExpense(expenseEntity: ExpenseEntity)
    fun updateExpense(expenseEntity: ExpenseEntity)
    fun deleteExpense(expenseEntity: ExpenseEntity)
}