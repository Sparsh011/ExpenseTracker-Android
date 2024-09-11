package com.sparshchadha.expensetracker.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sparshchadha.expensetracker.feature.expense_cards.data.local.room.dao.ExpenseCardDao
import com.sparshchadha.expensetracker.feature.expense_cards.domain.entity.ExpenseCardEntity

@Database(entities = [ExpenseCardEntity::class], version = 1)
abstract class ExpenseTrackerDatabase: RoomDatabase() {
    abstract fun expenseCardDao(): ExpenseCardDao

    companion object {
        const val DATABASE_NAME = "et_db"
    }
}