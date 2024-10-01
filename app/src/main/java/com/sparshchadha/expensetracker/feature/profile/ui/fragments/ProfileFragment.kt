package com.sparshchadha.expensetracker.feature.profile.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.Utility
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.core.navigation.NavigationProvider
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.screens.ProfileScreen
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    private lateinit var profileComposeView: ComposeView
    private var profileState = mutableStateOf<UserProfile?>(null)
    private var showError by mutableStateOf(false)
    private var showLoader by mutableStateOf(false)

    private var name by mutableStateOf("")
    private var expenseBudget by mutableDoubleStateOf(-1.0)

    @Inject
    lateinit var navigationProvider: NavigationProvider

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
                    profileViewModel.getUserProfile()
                },
                showLoader = showLoader,
                showError = showError,
                onNameUpdate = {
                    profileViewModel.updateUserName(it)
                },
                navigateToExpenseSettingsScreen = {
                    navigationProvider.navigateToExpenseSettingsFragment(
                        profileState.value?.expenseBudget?.toDouble() ?: -1.0
                    )
                },
                onLogout = {

                },
                navigateToNotificationsScreen = {
                    navigationProvider.navigateToNotificationsFragment()
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
}