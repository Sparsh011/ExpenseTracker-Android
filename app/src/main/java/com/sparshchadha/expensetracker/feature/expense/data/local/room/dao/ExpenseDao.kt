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

    @Query("SELECT SUM(amount) FROM ExpenseEntity")
    fun getLifetimeExpenditure(): Flow<Double>

    @Query("SELECT SUM(amount) FROM ExpenseEntity WHERE createdOnDate <= :finalDate AND createdOnDate >= :initialDate")
    fun getExpenditureBetweenDates(initialDate: String, finalDate: String): Flow<Long>

    @Query("SELECT * FROM ExpenseEntity LIMIT :n")
    fun getNRecentExpenses(n: Int): Flow<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM ExpenseEntity WHERE createdOnDate >= :dateNDaysAgo")
    fun getAmountSpentInLastNDays(dateNDaysAgo: String): Flow<Long>

    @Query("SELECT * FROM ExpenseEntity WHERE createdOnDate <= :finalDate AND createdOnDate >= :initialDate ORDER BY amount DESC LIMIT 5")
    fun getTop5ExpensesByAmountInDateRange(initialDate: String, finalDate: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM ExpenseEntity WHERE createdAtTime LIKE '%' || :searchQuery || '%'" +
            " OR createdOnDate LIKE '%' || :searchQuery || '%' " +
            "OR title LIKE '%' || :searchQuery || '%'" +
            "OR description LIKE '%' || :searchQuery || '%'"
    )
    fun getExpensesBySearchQuery(searchQuery: String): Flow<List<ExpenseEntity>>
}