package com.sparshchadha.expensetracker.feature.expense.data.repository

import com.sparshchadha.expensetracker.core.storage.datastore.ExpenseTrackerDataStorePreference
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import com.sparshchadha.expensetracker.feature.expense.data.local.room.dao.ExpenseDao
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseRepositoryImpl(
    private val dataStorePreference: ExpenseTrackerDataStorePreference,
    private val expenseDao: ExpenseDao,
    private val sharedPref: ExpenseTrackerSharedPref,
) : ExpenseRepository {
    override suspend fun getAllExpenses(expenseEntity: ExpenseEntity): Flow<List<ExpenseEntity>> =
        expenseDao.getAllExpenses()


    override fun saveExpense(expenseEntity: ExpenseEntity) {
        expenseDao.createNewExpense(expenseEntity)
    }

    override fun updateExpense(expenseEntity: ExpenseEntity) {
        expenseDao.updateExpense(expenseEntity)
    }

    override fun deleteExpense(expenseEntity: ExpenseEntity) {
        expenseDao.deleteExpense(expenseEntity)
    }
}