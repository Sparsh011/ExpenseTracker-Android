package com.sparshchadha.expensetracker.feature.transactions.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.core.navigation.NavigationProvider
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel.ExpenseViewModel
import com.sparshchadha.expensetracker.feature.transactions.presentation.ui.compose.screens.TransactionsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {
    private lateinit var transactionsComposeView: ComposeView

    private val expenseViewModel: ExpenseViewModel by viewModels()

    private var allExpenses = mutableStateListOf<ExpenseEntity>()

    @Inject
    lateinit var navigationProvider: NavigationProvider


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        observeAllExpenses()

        transactionsComposeView.setContent {
            TransactionsScreen(
                allExpenses = allExpenses.toList(),
                onExpenseClick = { expenseId ->
                    navigationProvider.navigateToExpenseFragment(expenseId)
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        transactionsComposeView = view.findViewById(R.id.transactions_compose_view)
    }

    private fun observeAllExpenses() {
        expenseViewModel.allExpenses.asLiveData().observe(viewLifecycleOwner) {
            it?.let { expenses ->
                allExpenses.clear()
                allExpenses.addAll(expenses)
            }
        }
    }
}