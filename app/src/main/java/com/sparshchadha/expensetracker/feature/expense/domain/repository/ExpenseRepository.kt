package com.sparshchadha.expensetracker.feature.expense.domain.repository

import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun getAllExpenses(): Flow<List<ExpenseEntity>>
    fun getCurrentDayExpenses(currentDate: String): Flow<List<ExpenseEntity>>
    fun getExpenseById(id: Int): Flow<ExpenseEntity>
    fun getExpensesBySearchQuery(searchQuery: String): Flow<List<ExpenseEntity>>

    fun getLifetimeExpenditure(): Flow<Double>
    fun getExpenditureBetweenDates(initialDate: String, finalDate: String): Flow<Long>
    fun getNRecentExpenses(n: Int): Flow<List<ExpenseEntity>>
    fun getAmountSpentInLastNDays(dateNDaysAgo: String): Flow<Long?>
    fun getTop5TransactionsByAmountInDateRange(initialDate: String, finalDate: String): Flow<List<ExpenseEntity>>

    fun saveExpense(expenseEntity: ExpenseEntity)
    fun updateExpense(expenseEntity: ExpenseEntity)
    fun deleteExpense(expenseEntity: ExpenseEntity)
}