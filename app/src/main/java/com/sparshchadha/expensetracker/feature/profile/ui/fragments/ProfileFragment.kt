package com.sparshchadha.expensetracker.feature.profile.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.common.utils.Utility
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.ProfileScreen
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens.ExpenseSettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    private lateinit var profileComposeView: ComposeView
    private var profileState = mutableStateOf<UserProfile?>(null)
    private var showError by mutableStateOf(false)
    private var showLoader by mutableStateOf(false)

    private var name by mutableStateOf("")
    private var expenseBudget by mutableIntStateOf(-1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        observeProfile()
        observeName()
        observeExpenseBudget()

        profileComposeView.setContent {
            ProfileScreen(
                profileState = profileState.value,
                userName = name,
                expenseBudget = expenseBudget,
                onBackPress = {
                    requireActivity().supportFragmentManager.popBackStack()
                },
                onRetryProfileFetch = {

                },
                showLoader = showLoader,
                showError = showError,
                onNameUpdate = {
                    profileViewModel.updateUserName(it)
                },
                navigateToExpenseSettingsScreen = {
                    navigateToExpenseSettingsScreen(profileState.value?.expenseBudget ?: -1)
                },
                onLogout = {

                },
                navigateToNotificationsScreen = {
                }
            )
        }
    }

    private fun observeExpenseBudget() {
        profileViewModel.expenseBudget.asLiveData().observe(viewLifecycleOwner) {
            this.expenseBudget = it
        }
    }

    private fun observeName() {
        profileViewModel.userName.asLiveData().observe(viewLifecycleOwner) {
            this.name = it
        }
    }


    private fun initializeViewsUsing(view: View) {
        profileComposeView = view.findViewById(R.id.profile_compose_view)
    }

    private fun observeProfile() {
        profileViewModel.userProfile.asLiveData().observe(viewLifecycleOwner) { profile ->
            profile?.let {
                when (it) {
                    is Resource.Error -> {
                        Utility.errorLog("observeProfile: ${it.error?.localizedMessage}")
                        showError = true
                        showLoader = false
                    }
                    is Resource.Loading -> {
                        showLoader = true
                    }
                    is Resource.Success -> {
                        showError = false
                        showLoader = false
                        profileState.value = it.data
                    }
                }
            }
        }
    }

    private fun navigateToExpenseSettingsScreen(currentExpenseBudget: Int) {
        val fragment = ExpenseSettingsFragment()
        fragment.arguments = Bundle().apply {
            putInt(BundleKeys.EXPENSE_BUDGET_KEY, currentExpenseBudget)
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .add(R.id.app_container, fragment)
            .addToBackStack("profile_fragment")
            .commit()
    }
}