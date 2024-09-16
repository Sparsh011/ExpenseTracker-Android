package com.sparshchadha.expensetracker.core.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sparshchadha.expensetracker.feature.expense.data.local.room.dao.ExpenseDao
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseTrackerDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "et_db"
    }
}