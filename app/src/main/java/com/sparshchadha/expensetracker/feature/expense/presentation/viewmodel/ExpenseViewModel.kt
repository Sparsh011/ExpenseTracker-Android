package com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private val _last30DaysAmountSpent = MutableStateFlow(-1.0)
    val last30DaysAmountSpent = _last30DaysAmountSpent.asStateFlow()

    private val _top5TransactionsByAmount = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val top5TransactionsByAmount = _top5TransactionsByAmount.asStateFlow()

    private var selectedExpenseId = -1

    private var searchQuery = ""

    private var expenseSearchDebouncingJob: Job? = null

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
        val currentDate = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER_PATTERN)).replace(' ', '-')
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

        val date = LocalDateTime.now().minusDays(30)
            .format(DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER_PATTERN))
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getAmountSpentInLastNDays(date).collect {
                _last30DaysAmountSpent.value = it?.toDouble() ?: 0.0
            }
        }
    }

    fun fetchTop5TransactionsByAmountInDateRange(initialDate: String, finalDate: String) {
        if (_top5TransactionsByAmount.value.isNotEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.getTop5TransactionsByAmountInDateRange(
                initialDate = initialDate,
                finalDate = finalDate
            ).collect {
                _top5TransactionsByAmount.value = it
            }
        }
    }

    /**
     * Performs debouncing at the provided interval before performing search.
     * @param debounceInterval Time interval at which debouncing should occur.
     * */
    fun searchExpenses(debounceInterval: Long = 400L) {
        expenseSearchDebouncingJob?.cancel()
        expenseSearchDebouncingJob = viewModelScope.launch(Dispatchers.IO) {
            delay(debounceInterval)
            if (searchQuery.isBlank()) {
                fetchAllExpenses()
                return@launch
            }
            expenseRepository.getExpensesBySearchQuery(searchQuery).collect {
                _allExpenses.value = it
            }
        }
    }

    fun setSearchQuery(searchQuery: String) {
        if (mightBeMonthString(searchQuery)) {
            this.searchQuery = mapMonthStrToInt(searchQuery)
        } else {
            this.searchQuery = searchQuery
        }
    }

    private fun mightBeMonthString(searchQuery: String): Boolean {
        val months = listOf(
            "jan", "feb", "mar", "apr", "may", "jun",
            "jul", "aug", "sep", "oct", "nov", "dec"
        )
        return months.contains(searchQuery.trim().lowercase().take(3))
    }

    private fun mapMonthStrToInt(searchQuery: String): String {
        val monthMap = mapOf(
            "jan" to "01", "feb" to "02", "mar" to "03",
            "apr" to "04", "may" to "05", "jun" to "06",
            "jul" to "07", "aug" to "08", "sep" to "09",
            "oct" to "10", "nov" to "11", "dec" to "12"
        )
        return monthMap[searchQuery.trim().lowercase().take(3)] ?: searchQuery
    }

    init {
        fetchAllExpenses()
        fetchCurrentDayExpenses()
    }
}