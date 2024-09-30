package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.common.utils.showToast
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseFragment : Fragment(R.layout.fragment_expense) {
    private val expenseViewModel by viewModels<ExpenseViewModel>()
    private val selectedExpense = mutableStateOf<ExpenseEntity?>(null)

    private lateinit var expenseComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        val expenseId = arguments?.getInt(BundleKeys.EXPENSE_DB_ID_KEY) ?: -1
        expenseViewModel.setSelectedExpenseId(expenseId)
        expenseViewModel.fetchExpenseById()
        observeSelectedExpense()

        expenseComposeView.setContent {
            ExpenseScreen(
                onSaveExpense = {
                    if (!it.isValid()) {
                        requireContext().showToast("Please fill ${it.getInvalidField()}")
                    }
                    if (selectedExpense.value == null) {
                        expenseViewModel.saveExpense(it)
                    } else {
                        expenseViewModel.updateExpense(it)
                    }
                    requireContext().showToast("Expense Saved!")
                    requireActivity().supportFragmentManager.popBackStack()
                },
                expenseEntity = selectedExpense.value,
                onCancel = {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        expenseComposeView = view.findViewById(R.id.expense_compose_view)
    }

    private fun observeSelectedExpense() {
        expenseViewModel.selectedExpense.asLiveData().observe(viewLifecycleOwner) {
            selectedExpense.value = it
        }
    }

}