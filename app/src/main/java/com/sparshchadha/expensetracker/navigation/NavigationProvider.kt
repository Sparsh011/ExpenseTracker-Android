package com.sparshchadha.expensetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.home.HomeFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ProfileFragment
import com.sparshchadha.expensetracker.feature.statistics.StatisticsFragment
import com.sparshchadha.expensetracker.feature.transactions.TransactionsFragment

object NavigationProvider {
    fun navigateToNotificationsScreen(
        navController: NavController,
    ) {
        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.slide_in).setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.slide_out)
        navController.navigate(
            route = NavGraph.nav_routes.notifications_screen,
            navOptions = navBuilder.build()
        )
    }

}