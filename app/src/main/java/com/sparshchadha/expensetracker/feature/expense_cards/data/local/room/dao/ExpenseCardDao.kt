package com.sparshchadha.expensetracker.feature.expense_cards.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sparshchadha.expensetracker.feature.expense_cards.domain.entity.ExpenseCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCardDao {

    @Query("SELECT * FROM ExpenseCardEntity")
    fun getAllCards(): Flow<List<ExpenseCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewCard(cardEntity: ExpenseCardEntity)

    @Delete
    fun deleteCard(cardEntity: ExpenseCardEntity)

    @Update
    fun updateCard(cardEntity: ExpenseCardEntity)

}