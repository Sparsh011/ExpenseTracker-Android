package com.sparshchadha.expensetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.feature.home.HomeFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ProfileFragment
import com.sparshchadha.expensetracker.feature.statistics.StatisticsFragment
import com.sparshchadha.expensetracker.feature.transactions.TransactionsFragment

object NavGraph {
    object nav_routes {
        const val home = "home"
        const val transactions = "transactions"
        const val profile = "profile"
        const val statistics = "statistics"
        const val notifications_screen = "notifications_screen"
    }

    fun createNavGraph(navController: NavController): androidx.navigation.NavGraph {
        return navController.createGraph(
            startDestination = NavGraph.nav_routes.home
        ) {
            fragment<HomeFragment>(NavGraph.nav_routes.home)

            fragment<TransactionsFragment>(NavGraph.nav_routes.transactions)

            fragment<StatisticsFragment>(NavGraph.nav_routes.statistics)

            fragment<ProfileFragment>(NavGraph.nav_routes.profile)

            fragment<NotificationsFragment>(NavGraph.nav_routes.notifications_screen)
        }
    }
}