package com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel

import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.common.utils.Constants
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

    private val _selectedExpense = MutableStateFlow<ExpenseEntity?>(null)
    val selectedExpense = _selectedExpense.asStateFlow()

    private var selectedExpenseId = -1

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
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER_PATTERN)).lowercase()
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getCurrentDayExpenses(currentDate).collect {
                _currentDayExpenses.value = it
            }
        }
    }

    fun fetchExpenseById() {
        if (selectedExpenseId == -1) return
        if (_selectedExpense.value != null) return
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getExpenseById(selectedExpenseId).collect {
                _selectedExpense.value = it
            }
        }
    }

    fun setSelectedExpenseId(id: Int) {
        if (selectedExpenseId == -1) selectedExpenseId = id
    }

    init {
        fetchAllExpenses()
        fetchCurrentDayExpenses()
    }
}