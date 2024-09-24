package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseFragment : Fragment(R.layout.fragment_expense) {
    private val expenseViewModel by viewModels<ExpenseViewModel>()

    private lateinit var expenseComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        expenseComposeView.setContent {
            ExpenseScreen(
                onSaveExpense = {
                    expenseViewModel.saveExpense(it)
                    Toast.makeText(requireContext(), "Expense created", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                },
                expenseEntity = null,
                onCancel = {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        expenseComposeView = view.findViewById(R.id.expense_compose_view)
    }

}