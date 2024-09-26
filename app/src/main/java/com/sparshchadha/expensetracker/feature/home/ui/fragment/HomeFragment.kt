package com.sparshchadha.expensetracker.feature.home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens.ExpenseFragment
import com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel.ExpenseViewModel
import com.sparshchadha.expensetracker.feature.home.ui.compose.screen.HomeScreen
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ui.fragments.ProfileFragment
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val expenseViewModel by viewModels<ExpenseViewModel>()

    private lateinit var homeComposeView: ComposeView
    private lateinit var createExpenseFab: FloatingActionButton

    private var userName by mutableStateOf("")
    private var profileUri by mutableStateOf("")
    private var expenseBudget by mutableDoubleStateOf(-1.0)
    private val currentDayExpenses = mutableStateListOf<ExpenseEntity>()
    private var amountSpentInLast30Days by mutableDoubleStateOf(0.0)
    private val top5TransactionsByAmountSpent = mutableStateListOf<ExpenseEntity>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        fetchRelevantExpenses()
        addObservers()

        homeComposeView.setContent {
            HomeScreen(
                navigateToNotificationsFragment = {
                    navigateToNotificationsFragment()
                },
                navigateToProfileFragment = {
                    navigateToProfileFragment()
                },
                expenseBudget = expenseBudget,
                userName = userName,
                profileUri = profileUri,
                allExpenses = currentDayExpenses,
                onExpenseItemClick = { expenseId ->
                    navigateToExpenseScreen(expenseId)
                },
                amountSpentInLast30Days = amountSpentInLast30Days,
                top5TransactionsByAmountSpent = top5TransactionsByAmountSpent
            )
        }
    }

    private fun fetchRelevantExpenses() {
        expenseViewModel.fetchLast30DaysAmountSpent()
        expenseViewModel.fetchTop5TransactionsByAmountInDateRange(
            initialDate = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER_PATTERN)),
            finalDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER_PATTERN)),
        )
    }


    private fun initializeViewsUsing(view: View) {
        homeComposeView = view.findViewById(R.id.home_compose_view)
        createExpenseFab = view.findViewById(R.id.fab_create_expense)

        createExpenseFab.setOnClickListener { navigateToExpenseScreen() }
    }

    private fun navigateToExpenseScreen(id: Int = -1) {
        val fragment = ExpenseFragment()
        if (id != -1) {
            val bundle = Bundle()
            bundle.putInt(BundleKeys.EXPENSE_DB_ID_KEY, id)
            fragment.arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_down
            )
            .add(R.id.app_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addObservers() {
        observeUserName()
        observeProfileUri()
        observeExpenseBudget()
        observeCurrentDayExpenses()
        observeAmountSpendInLast30Days()
        observeTop5TransactionsByAmountSpent()
    }

    private fun observeAmountSpendInLast30Days() {
        expenseViewModel.last30DaysAmountSpent.asLiveData().observe(viewLifecycleOwner) {
            amountSpentInLast30Days = if (it == -1.0) 0.0 else it
        }
    }

    private fun observeExpenseBudget() {
        profileViewModel.expenseBudget.asLiveData().observe(viewLifecycleOwner) {
            expenseBudget = it
        }
    }

    private fun observeProfileUri() {
        profileViewModel.profileUri.asLiveData().observe(viewLifecycleOwner) {
            profileUri = it
        }
    }

    private fun observeUserName() {
        profileViewModel.userName.asLiveData().observe(viewLifecycleOwner) {
            userName = it
        }
    }

    private fun observeCurrentDayExpenses() {
        expenseViewModel.currentDayExpenses.asLiveData().observe(viewLifecycleOwner) {
            it?.let { expenses ->
                currentDayExpenses.clear()
                currentDayExpenses.addAll(expenses)
            }
        }
    }

    private fun observeTop5TransactionsByAmountSpent() {
        expenseViewModel.top5TransactionsByAmount.asLiveData().observe(viewLifecycleOwner) {
            it?.let { expenses ->
                top5TransactionsByAmountSpent.clear()
                top5TransactionsByAmountSpent.addAll(expenses)
            }
        }
    }


    private fun navigateToNotificationsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, NotificationsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToProfileFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, ProfileFragment())
            .addToBackStack(null)
            .commit()
    }
}