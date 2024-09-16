package com.sparshchadha.expensetracker.feature.profile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.ProfileScreen
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens.ExpenseSettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var profileComposeView: ComposeView
    private var profileState = mutableStateOf<UserProfile?>(null)
    private var showError by mutableStateOf(false)
    private var showLoader by mutableStateOf(false)
    private var isProfileFetchedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.let {
            isProfileFetchedOnce = it.getBoolean("isProfileFetchedOnce", false)
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isProfileFetchedOnce", isProfileFetchedOnce)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        fetchProfile()
        observeProfile()

        profileComposeView.setContent {
            ProfileScreen(
                profileState = profileState.value,
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
                    navigateToExpenseSettingsScreen()
                }
            )
        }
    }


    private fun initializeViewsUsing(view: View) {
        profileComposeView = view.findViewById(R.id.profile_compose_view)
    }

    private fun fetchProfile() {
        if (!isProfileFetchedOnce) {
            profileViewModel.getUserProfile()
            isProfileFetchedOnce = true
        }
    }

    private fun observeProfile() {
        profileViewModel.userProfile.asLiveData().observe(viewLifecycleOwner) { profile ->
            profile?.let {
                when (it) {
                    is Resource.Error -> {
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

    private fun navigateToExpenseSettingsScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, ExpenseSettingsFragment())
            .addToBackStack("profile_fragment")
            .commit()
    }
}