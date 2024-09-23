package com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    private val _allExpenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val allExpenses = _allExpenses.asStateFlow()

    private val _currentDayExpenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val currentDayExpenses = _currentDayExpenses.asStateFlow()

    private fun fetchAllExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getAllExpenses().collect {
                _allExpenses.value = it
            }
        }
    }

    fun saveExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.saveExpense(expenseEntity)
        }
    }

    fun updateExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.updateExpense(expenseEntity)
        }
    }

    fun deleteExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.deleteExpense(expenseEntity)
        }
    }

    private fun fetchCurrentDayExpenses() {
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getCurrentDayExpenses(currentDate).collect {
                _currentDayExpenses.value = it
            }
        }
    }

    init {
        fetchAllExpenses()
        fetchCurrentDayExpenses()
    }
}