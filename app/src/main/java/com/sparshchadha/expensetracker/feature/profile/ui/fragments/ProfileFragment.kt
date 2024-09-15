package com.sparshchadha.expensetracker.feature.profile.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.ProfileScreen
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import com.sparshchadha.expensetracker.common.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var profileComposeView: ComposeView
    private var profileState = mutableStateOf<Resource<UserProfile>?>(null)

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

                }
            )
        }
    }


    private fun initializeViewsUsing(view: View) {
        profileComposeView = view.findViewById(R.id.profile_compose_view)
    }

    private fun observeProfile() {
        profileViewModel.userProfile.asLiveData().observe(viewLifecycleOwner) {
            this.profileState.value = it
        }
    }
}