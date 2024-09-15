package com.sparshchadha.expensetracker.feature.expense_cards.data.repository

import com.sparshchadha.expensetracker.feature.expense_cards.data.local.room.dao.ExpenseCardDao
import com.sparshchadha.expensetracker.feature.expense_cards.domain.entity.ExpenseCardEntity
import com.sparshchadha.expensetracker.feature.expense_cards.domain.repository.ExpenseCardRepository
import com.sparshchadha.expensetracker.core.storage.datastore.ExpenseTrackerDataStorePreference
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseCardRepositoryImpl(
    private val dataStorePreference: ExpenseTrackerDataStorePreference,
    private val expenseCardDao: ExpenseCardDao,
    private val sharedPref: ExpenseTrackerSharedPref
) : ExpenseCardRepository {
    override suspend fun getTotalExpenseCards(): Flow<Byte> = flow {
        dataStorePreference.readTotalExpenseCards.collect { cardsStr ->
            cardsStr?.let {
                emit(it.toByte())
            }
        }
    }

    override suspend fun setTotalExpenseCards(n: Byte) {
        dataStorePreference.saveTotalExpenseCards(n.toString())
    }

    override suspend fun getAllExpenseCards(): Flow<List<ExpenseCardEntity>> = flow {
        expenseCardDao.getAllCards().collect {
            emit(it)
        }
    }

    override suspend fun createExpenseCard(cardEntity: ExpenseCardEntity) =
        expenseCardDao.createNewCard(cardEntity)


    override suspend fun deleteExpenseCard(cardEntity: ExpenseCardEntity) =
        expenseCardDao.deleteCard(cardEntity)

    override suspend fun updateExpenseCard(cardEntity: ExpenseCardEntity) =
        expenseCardDao.updateCard(cardEntity)

    override fun getUserId(): String {
        return sharedPref.getUserId()
    }
}