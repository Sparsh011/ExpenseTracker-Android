package com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel

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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
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

    private val _last30DaysAmountSpent = MutableStateFlow(-1.0)
    val last30DaysAmountSpent = _last30DaysAmountSpent.asStateFlow()

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
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER_PATTERN))
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

    fun fetchLast30DaysAmountSpent() {
        if (_last30DaysAmountSpent.value != -1.0) return

        val date = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER_PATTERN))
        val compatibleDate = convertToISOFormat(date)
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getAmountSpentInLastNDays(compatibleDate).collect {
                _last30DaysAmountSpent.value = it?.toDouble() ?: 0.0
            }
        }
    }

    private fun convertToISOFormat(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(dateString)

        return outputFormat.format(date)
    }

    init {
        fetchAllExpenses()
        fetchCurrentDayExpenses()
    }
}