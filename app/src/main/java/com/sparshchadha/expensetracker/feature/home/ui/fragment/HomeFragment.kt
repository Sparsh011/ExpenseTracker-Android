package com.sparshchadha.expensetracker.feature.home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.showToast
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.home.ui.compose.screen.HomeScreen
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.fragments.ProfileFragment
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.exp

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private lateinit var homeComposeView: ComposeView
    private var isNoTransactionsAnimShown = false
    private val profileViewModel by viewModels<ProfileViewModel>()
    private var userName by mutableStateOf("")
    private var profileUri by mutableStateOf("")
    private var expenseBudget by mutableIntStateOf(-1)


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
                userName = userName
            )
        }
    }


    private fun initializeViewsUsing(view: View) {
        homeComposeView = view.findViewById(R.id.home_compose_view)
    }

    private fun addObservers() {
        observeUserName()
        observeProfileUri()
        observeExpenseBudget()
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