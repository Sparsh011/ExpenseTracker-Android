package com.sparshchadha.expensetracker.feature.home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens.ExpenseFragment
import com.sparshchadha.expensetracker.feature.expense.presentation.viewmodel.ExpenseViewModel
import com.sparshchadha.expensetracker.feature.home.ui.compose.screen.HomeScreen
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ui.fragments.ProfileFragment
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val expenseViewModel by viewModels<ExpenseViewModel>()

    private lateinit var homeComposeView: ComposeView
    private lateinit var createExpenseFab: FloatingActionButton

    private var isNoTransactionsAnimShown = false

    private var userName by mutableStateOf("")
    private var profileUri by mutableStateOf("")
    private var expenseBudget by mutableIntStateOf(-1)
    private var currentDayExpenses = mutableListOf<ExpenseEntity>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        addObservers()

        homeComposeView.setContent {
            HomeScreen(
                navigateToNotificationsFragment = {
                    navigateToNotificationsFragment()
                },
                navigateToProfileFragment = {
                    navigateToProfileFragment()
                },
                isNoTransactionsAnimShown = isNoTransactionsAnimShown,
                expenseBudget = expenseBudget,
                profileUri = profileUri,
                userName = userName,
                allExpenses = currentDayExpenses.toList()
            )
        }
    }


    private fun initializeViewsUsing(view: View) {
        homeComposeView = view.findViewById(R.id.home_compose_view)
        createExpenseFab = view.findViewById(R.id.fab_create_expense)

        createExpenseFab.setOnClickListener { navigateToExpenseCreationScreen() }
    }

    private fun navigateToExpenseCreationScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_down
            )
            .add(R.id.app_container, ExpenseFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun addObservers() {
        observeUserName()
        observeProfileUri()
        observeExpenseBudget()
        observeCurrentDayExpenses()
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
                currentDayExpenses = expenses.toMutableList()
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

    override fun onPause() {
        super.onPause()
        // Set isNoTransactionsAnimShown to true here so that whenever we come back to this fragment,
        // animation is not shown again and again
        isNoTransactionsAnimShown = true
    }
}