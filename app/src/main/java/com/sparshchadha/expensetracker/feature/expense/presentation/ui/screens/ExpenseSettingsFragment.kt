package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseSettingsFragment : Fragment(R.layout.fragment_expense_settings) {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var expenseSettingsComposeView: ComposeView
    private var expenseBudget by mutableIntStateOf(-1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(usingView = view)

        addObservers()

        expenseSettingsComposeView.setContent {
            ExpenseSettingsScreen(
                currentExpenseBudget = expenseBudget,
                onChangeBudget = {
                    profileViewModel.updateExpenseBudget(newBudget = it)
                },
                onBackPress = {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            )
        }
    }

    private fun initializeViews(usingView: View) {
        expenseSettingsComposeView = usingView.findViewById(R.id.expense_settings_cv)
    }

    private fun addObservers() {
        profileViewModel.expenseBudget.asLiveData().observe(viewLifecycleOwner) { expenseBudget ->
            this.expenseBudget = expenseBudget
        }
    }
}