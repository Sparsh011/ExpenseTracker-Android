package com.sparshchadha.expensetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.sparshchadha.expensetracker.R

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