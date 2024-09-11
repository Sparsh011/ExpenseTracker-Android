package com.sparshchadha.expensetracker.feature.expense_cards.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.expense_cards.domain.entity.ExpenseCardEntity
import com.sparshchadha.expensetracker.feature.expense_cards.domain.repository.ExpenseCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseCardViewModel @Inject constructor(
    private val expenseCardRepository: ExpenseCardRepository
): ViewModel() {
    private val _totalExpenseCards = MutableStateFlow<Byte>(-1)
    val totalExpenseCards = _totalExpenseCards.asStateFlow()

    private val _allExpenseCards = MutableStateFlow<List<ExpenseCardEntity>?>(null)
    val allExpenseCards = _allExpenseCards.asStateFlow()

    fun getAllExpenseCards() {
        viewModelScope.launch(Dispatchers.IO) {
            expenseCardRepository.getAllExpenseCards().collect {
                _allExpenseCards.value = it
            }
        }
    }

    fun setTotalExpenseCards(n: Byte) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseCardRepository.setTotalExpenseCards(n)
        }
    }

    fun getTotalExpenseCards() {
        viewModelScope.launch(Dispatchers.IO) {
            expenseCardRepository.getTotalExpenseCards().collect {
                _totalExpenseCards.value = it
            }
        }
    }

    fun syncCardsWithServer() {
        TODO()
    }

    fun createExpenseCard(
        cardName: String,
        cardLimit: Int,
        currentSpending: Int,
        isPrimary: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val expenseCard = ExpenseCardEntity(cardLimit = cardLimit, cardName = cardName, currentSpending = currentSpending, isPrimary = isPrimary)
            expenseCardRepository.createExpenseCard(cardEntity = expenseCard)
        }
    }


}