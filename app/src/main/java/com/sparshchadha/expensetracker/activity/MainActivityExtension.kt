package com.sparshchadha.expensetracker.activity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavDestination
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.fragments.LoginFragment
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.onboarding.OnboardingFragment
import com.sparshchadha.expensetracker.feature.profile.ui.ProfileFragment
import com.sparshchadha.expensetracker.navigation.ExpenseTrackerNavGraph

internal fun MainActivity.setGlobalNavGraph(
    startDestination: String
) {
    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.parent_fragment_container) as NavHostFragment
    parentNavController = navHostFragment.navController

    parentNavController.graph = parentNavController.createGraph(
        startDestination = startDestination
    ) {
        fragment<OnboardingFragment>(ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN)

        fragment<MainBottomNavigationBarFragment>(ExpenseTrackerNavGraph.MainRoutes.MAIN_BOTTOM_NAVIGATION_SCREEN)

        fragment<NotificationsFragment>(ExpenseTrackerNavGraph.MainRoutes.NOTIFICATIONS_SCREEN)

        fragment<ProfileFragment>(ExpenseTrackerNavGraph.MainRoutes.PROFILE_SCREEN)

        fragment<LoginFragment>(ExpenseTrackerNavGraph.MainRoutes.LOGIN_SCREEN)
    }
}


internal fun MainActivity.setWindowAttributes() {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_container)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.updatePadding(bottom = systemBars.bottom)
        insets
    }

    WindowCompat.getInsetsController(
        window,
        findViewById(R.id.app_container)
    ).isAppearanceLightStatusBars = true
}