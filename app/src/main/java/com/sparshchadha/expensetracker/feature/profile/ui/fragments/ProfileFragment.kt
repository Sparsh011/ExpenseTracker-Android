package com.sparshchadha.expensetracker.feature.profile.ui.fragments

import android.os.Bundle
import android.view.View
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var profileComposeView: ComposeView
    private var profileState = mutableStateOf<UserProfile?>(null)
    private var showError by mutableStateOf(false)
    private var showLoader by mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

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
                }
            )
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