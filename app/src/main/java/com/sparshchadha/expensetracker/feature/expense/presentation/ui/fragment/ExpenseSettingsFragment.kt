package com.sparshchadha.expensetracker.feature.expense.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.core.navigation.NavigationProvider
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.screens.ExpenseSettingsScreen
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExpenseSettingsFragment : Fragment(R.layout.fragment_expense_settings) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private lateinit var expenseSettingsComposeView: ComposeView
    private lateinit var expenseBudget : MutableDoubleState

    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(usingView = view)

        addObservers()

        expenseBudget = mutableDoubleStateOf(arguments?.getDouble(BundleKeys.EXPENSE_BUDGET_KEY) ?: -1.0)

        expenseSettingsComposeView.setContent {
            ExpenseSettingsScreen(
                currentExpenseBudget = expenseBudget.doubleValue,
                onChangeBudget = {
                    navigationProvider.showLoadingFragment()
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
            navigationProvider.dismissLoadingFragment()
            this.expenseBudget.doubleValue = expenseBudget
        }
    }
}