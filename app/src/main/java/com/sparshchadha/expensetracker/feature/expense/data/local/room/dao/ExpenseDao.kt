package com.sparshchadha.expensetracker.feature.expense.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM ExpenseEntity")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewExpense(expense: ExpenseEntity)

    @Delete
    fun deleteExpense(expense: ExpenseEntity)

    @Update
    fun updateExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM ExpenseEntity WHERE createdOnDate = :currentDate")
    fun getExpensesForCurrentDay(currentDate: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM ExpenseEntity WHERE expenseId = :id")
    fun getExpenseById(id: Int): Flow<ExpenseEntity>
}