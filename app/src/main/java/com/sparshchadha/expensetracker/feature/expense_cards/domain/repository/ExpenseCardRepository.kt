package com.sparshchadha.expensetracker.feature.expense_cards.domain.repository

import com.sparshchadha.expensetracker.feature.expense_cards.domain.entity.ExpenseCardEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseCardRepository {
    fun getUserId(): String

    suspend fun getTotalExpenseCards(): Flow<Byte>
    suspend fun setTotalExpenseCards(n: Byte)

    suspend fun getAllExpenseCards(): Flow<List<ExpenseCardEntity>>
    suspend fun createExpenseCard(cardEntity: ExpenseCardEntity)
    suspend fun deleteExpenseCard(cardEntity: ExpenseCardEntity)
    suspend fun updateExpenseCard(cardEntity: ExpenseCardEntity)
}