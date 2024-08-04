package com.sparshchadha.expensetracker.activity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.fragments.LoginFragment
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarScreen
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.onboarding.OnboardingFragment
import com.sparshchadha.expensetracker.feature.profile.ProfileFragment
import com.sparshchadha.expensetracker.navigation.ExpenseTrackerNavGraph

internal fun MainActivity.setGlobalNavGraph() {
    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.parent_fragment_container) as NavHostFragment
    parentNavController = navHostFragment.navController

    parentNavController.graph = parentNavController.createGraph(
        startDestination = ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN
    ) {
        fragment<OnboardingFragment>(ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN)

        fragment<MainBottomNavigationBarScreen>(ExpenseTrackerNavGraph.MainRoutes.MAIN_BOTTOM_NAVIGATION_SCREEN)

        fragment<NotificationsFragment>(ExpenseTrackerNavGraph.MainRoutes.NOTIFICATIONS_SCREEN)

        fragment<ProfileFragment>(ExpenseTrackerNavGraph.MainRoutes.PROFILE_SCREEN)

        fragment<LoginFragment>(ExpenseTrackerNavGraph.MainRoutes.LOGIN_SCREEN)
    }
}


internal fun MainActivity.setWindowAttributes() {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_container)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
        insets
    }
}